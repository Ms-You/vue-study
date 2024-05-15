package com.board.api.service;

import com.board.api.config.SecurityUtil;
import com.board.api.config.exception.ErrorCode;
import com.board.api.config.exception.GlobalException;
import com.board.api.domain.CartItem;
import com.board.api.domain.Item;
import com.board.api.domain.Member;
import com.board.api.domain.dto.CartItemDTO;
import com.board.api.repository.CartItemRepository;
import com.board.api.repository.ItemRepository;
import com.board.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartItemService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    /**
     * 장바구니에 상품 추가
     * @param itemId
     */
    @Transactional
    public void pushItemIntoCart(Long itemId) {
        Member member = memberRepository.findByEmail(SecurityUtil.getCurrentMember()).orElseThrow(() -> {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        });

        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            throw new GlobalException(ErrorCode.ITEM_NOT_FOUND);
        });

        CartItem cartItem = CartItem.builder()
                .cart(member.getCart())
                .item(item)
                .build();

        cartItemRepository.save(cartItem);

        member.getCart().addCartItem(cartItem);
    }

    /**
     * 장바구니에 속한 상품 목록 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<CartItemDTO.CartItemResp> getCartItems() {
        List<CartItem> cartItemList = cartItemRepository.findAll();

        return cartItemList.stream()
                .map(cartItem -> CartItemDTO.CartItemResp.builder()
                        .id(cartItem.getItem().getId())
                        .name(cartItem.getItem().getName())
                        .imgPath(cartItem.getItem().getImgPath())
                        .price(cartItem.getItem().getPrice())
                        .discountRate(cartItem.getItem().getDiscountRate())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 장바구니에 속한 상품 삭제
     * @param itemId
     */
    @Transactional
    public void deleteCartItem(Long itemId) {
        Member member = memberRepository.findByEmail(SecurityUtil.getCurrentMember()).orElseThrow(() -> {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        });

        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            throw new GlobalException(ErrorCode.ITEM_NOT_FOUND);
        });

        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(member.getCart().getId(), item.getId()).orElseThrow(() -> {
            throw new GlobalException(ErrorCode.CART_ITEM_NOT_FOUND);
        });

        cartItemRepository.delete(cartItem);
    }

}
