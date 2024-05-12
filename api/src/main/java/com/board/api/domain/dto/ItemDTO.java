package com.board.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ItemDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemResp {
        private String name;
    }
}
