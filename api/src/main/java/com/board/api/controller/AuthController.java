package com.board.api.controller;

import com.board.api.config.common.BasicResponse;
import com.board.api.config.jwt.TokenDTO;
import com.board.api.domain.dto.MemberDTO;
import com.board.api.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final MemberService memberService;

    /**
     * 회원가입
     * @param joinReq
     * @return
     */
    @PostMapping("/auth")
    public ResponseEntity<BasicResponse> registerAccount(@RequestBody MemberDTO.JoinReq joinReq) {
        memberService.register(joinReq);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.CREATED.value(), "회원가입이 완료되었습니다."));
    }

    /**
     * 이메일 중복 여부 확인
     * @param email
     * @return
     */
    @GetMapping("/auth/check-email")
    public ResponseEntity<BasicResponse> checkEmailDuplicated(@RequestParam(name = "email") String email) {
        memberService.checkEmail(email);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "사용 가능한 이메일입니다."));
    }

    /**
     * 로그인
     * @param loginReq
     * @param response
     * @return
     */
    @PostMapping("/auth/login")
    public ResponseEntity<BasicResponse> login(@RequestBody MemberDTO.LoginReq loginReq, HttpServletResponse response) {
        TokenDTO tokenDTO = memberService.signIn(loginReq);

        Cookie refreshTokenCookie = new Cookie("RefreshToken", tokenDTO.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7);

        response.setHeader("Authorization", "Bearer " + tokenDTO.getAccessToken());
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "로그인 성공"));
    }

    /**
     * 로그아웃
     * @param request
     * @return
     */
    @PostMapping("/auth/logout")
    public ResponseEntity<BasicResponse> logout(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            memberService.logout(bearerToken.substring(7));
        }

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "로그아웃 되었습니다."));
    }

}
