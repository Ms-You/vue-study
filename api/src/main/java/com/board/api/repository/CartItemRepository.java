package com.board.api.repository;

import com.board.api.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndItemId(Long cartId, Long itemId);
    void deleteByCartId(Long cartId);
    List<CartItem> findAllByCartId(Long cartId);
}
