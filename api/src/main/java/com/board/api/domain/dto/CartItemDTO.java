package com.board.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CartItemDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CartItemResp {
        private Long id;
        private String name;
        private String imgPath;
        private int price;
        private int discountRate;
    }
}
