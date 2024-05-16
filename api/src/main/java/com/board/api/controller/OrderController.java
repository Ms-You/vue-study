package com.board.api.controller;

import com.board.api.config.common.BasicResponse;
import com.board.api.domain.dto.OrderDTO;
import com.board.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    /**
     * 주문 생성
     * @param orderReq
     * @return
     */
    @PostMapping("/member/order")
    public ResponseEntity<BasicResponse> makeOrder(@RequestBody OrderDTO.OrderReq orderReq) {
        orderService.makeOrder(orderReq);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.CREATED.value(), "주문이 완료되었습니다."));
    }

    /**
     * 주문 내역 조회
     * @return
     */
    @GetMapping("/member/order")
    public ResponseEntity<BasicResponse> getOrder() {
        List<OrderDTO.OrderResp> orderRespList = orderService.getOrder();

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "주문 내역 조회", orderRespList));
    }
}
