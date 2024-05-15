package com.board.api.controller;

import com.board.api.config.common.BasicResponse;
import com.board.api.domain.dto.CartItemDTO;
import com.board.api.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartItemController {
    private final CartItemService cartItemService;

    /**
     * 장바구니에 상품 추가
     * @param itemId
     * @return
     */
    @PostMapping("/member/cart/item/{itemId}")
    public ResponseEntity<BasicResponse> pushCartItem(@PathVariable Long itemId) {
        cartItemService.pushItemIntoCart(itemId);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "상품이 추가되었습니다."));
    }

    /**
     * 장바구니에 속한 상품 목록 조회
     * @return
     */
    @GetMapping("/member/cart/item")
    public ResponseEntity<BasicResponse> getCartItems() {
        List<CartItemDTO.CartItemResp> cartItemRespList = cartItemService.getCartItems();

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "상품 목록 반환", cartItemRespList));
    }

}
