package com.board.api.controller;

import com.board.api.domain.dto.MemberDTO;
import com.board.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final MemberService memberService;

    // Vue 로그인 시 axios 호출 테스트
    @PostMapping("/api/account/login")
    public Long signIn(@RequestBody MemberDTO.LoginReq loginReq) {
        Long id = memberService.signIn(loginReq);
        if(id == 0L) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return id;
    }


}
