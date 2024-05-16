package com.board.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderDTO {

    @Getter
    @NoArgsConstructor
    public static class OrderReq {
        private String name;
        private String address;
        private String payMethod;
        private String cardNumber;
        private List<OrderItemReq> orderItems;
    }

    @Getter
    @NoArgsConstructor
    public static class OrderItemReq {
        private Long itemId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderResp {
        private Long id;
        private String name;
        private String address;
        private String payMethod;
        private String cardNumber;
        private List<OrderItemResp> orderItems;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemResp {
        private Long id;
        private String name;
        private int price;
    }
}
