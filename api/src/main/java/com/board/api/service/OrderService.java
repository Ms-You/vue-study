package com.board.api.service;

import com.board.api.config.SecurityUtil;
import com.board.api.config.exception.ErrorCode;
import com.board.api.config.exception.GlobalException;
import com.board.api.domain.Member;
import com.board.api.domain.Order;
import com.board.api.domain.OrderItem;
import com.board.api.domain.dto.OrderDTO;
import com.board.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문 정보 생성
     * @param orderReq
     */
    @Transactional
    public void makeOrder(OrderDTO.OrderReq orderReq) {
        Member member = memberRepository.findByEmail(SecurityUtil.getCurrentMember()).orElseThrow(() -> {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        });

        Order order = Order.builder()
                .name(orderReq.getName())
                .address(orderReq.getAddress())
                .payMethod(orderReq.getPayMethod())
                .cardNumber(orderReq.getCardNumber())
                .member(member)
                .build();

        orderRepository.save(order);

        orderReq.getOrderItems().stream()
                .map(orderItemReq -> itemRepository.findById(orderItemReq.getItemId()).orElseThrow(() -> {
                    throw new GlobalException(ErrorCode.ITEM_NOT_FOUND);
                }))
                .forEach(item -> orderItemRepository.save(OrderItem.builder()
                        .order(order)
                        .item(item)
                        .build()));

        cartItemRepository.deleteByCartId(member.getCart().getId());
    }

    /**
     * 주문 내역 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<OrderDTO.OrderResp> getOrder() {
        Member member = memberRepository.findByEmail(SecurityUtil.getCurrentMember()).orElseThrow(() -> {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        });

        List<Order> orderList = orderRepository.findByMemberWithOrderItems(member);

        return orderList.stream()
                .map(order -> OrderDTO.OrderResp.builder()
                        .id(order.getId())
                        .name(order.getName())
                        .address(order.getAddress())
                        .payMethod(order.getPayMethod())
                        .cardNumber(order.getCardNumber())
                        .orderItems(order.getOrderItemList().stream()
                                .map(orderItem -> OrderDTO.OrderItemResp.builder()
                                        .id(orderItem.getItem().getId())
                                        .name(orderItem.getItem().getName())
                                        .price(orderItem.getItem().getPrice())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

}
