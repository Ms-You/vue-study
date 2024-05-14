package com.board.api.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDTO {

    @Getter
    @NoArgsConstructor
    public static class JoinReq {
        private String email;
        private String password;
        private String passwordConfirm;
        private String nickName;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginReq {
        private String email;
        private String password;
    }
}
