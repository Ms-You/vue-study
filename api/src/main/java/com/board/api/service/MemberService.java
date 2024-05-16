package com.board.api.service;

import com.board.api.config.exception.ErrorCode;
import com.board.api.config.exception.GlobalException;
import com.board.api.config.jwt.TokenDTO;
import com.board.api.config.jwt.TokenProvider;
import com.board.api.domain.Member;
import com.board.api.domain.RoleType;
import com.board.api.domain.dto.MemberDTO;
import com.board.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 회원가입
     * @param joinReq
     */
    @Transactional
    public void register(MemberDTO.JoinReq joinReq) {
        checkEmail(joinReq.getEmail());

        Member member = Member.builder()
                .email(joinReq.getEmail())
                .password(passwordEncoder.encode(joinReq.getPassword()))
                .nickName(joinReq.getNickName())
                .role(RoleType.ROLE_USER)
                .build();

        memberRepository.save(member);
    }

    /**
     * 이메일 중복 여부 체크
     * @param email
     */
    @Transactional(readOnly = true)
    public void checkEmail(String email) {
        memberRepository.findByEmail(email).ifPresent(a -> {
            throw new GlobalException(ErrorCode.EMAIL_ALREADY_EXISTS);
        });
    }

    /**
     * 로그인
     * @param loginReq
     * @return
     */
    @Transactional
    public TokenDTO signIn(MemberDTO.LoginReq loginReq) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDTO tokenDTO = tokenProvider.generateToken(authentication);

        redisTemplate.opsForValue().set(
                authentication.getName(),
                tokenDTO.getRefreshToken(),
                tokenProvider.getExpiration(tokenDTO.getRefreshToken()),
                TimeUnit.MILLISECONDS
        );

        return tokenDTO;
    }

    /**
     * 로그아웃
     * @param accessToken
     */
    @Transactional
    public void logout(String accessToken) {
        // 토큰이 유효한지 확인
        if(!tokenProvider.validateToken(accessToken)) {
            throw new GlobalException(ErrorCode.TOKEN_NOT_VALIDATE);
        }

        // 인증 객체 추출
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        // redis에 토큰 정보가 존재하면 삭제
        if(redisTemplate.opsForValue().get(authentication.getName()) != null) {
            redisTemplate.delete(authentication.getName());
        }

        // 해당 토큰을 더 이상 사용할 수 없도록 필터에서 체크
        redisTemplate.opsForValue().set(
                accessToken,
                "logout",
                tokenProvider.getExpiration(accessToken),
                TimeUnit.MILLISECONDS
        );
    }

}
