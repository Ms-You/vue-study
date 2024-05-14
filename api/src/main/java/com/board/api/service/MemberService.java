package com.board.api.service;

import com.board.api.domain.Member;
import com.board.api.domain.dto.MemberDTO;
import com.board.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long signIn(MemberDTO.LoginReq loginReq) {
        Optional<Member> memberOptional = memberRepository.findByEmail(loginReq.getEmail());

        if(memberOptional.isPresent()) {
            return memberOptional.get().getId();
        }

        return 0L;
    }

}
