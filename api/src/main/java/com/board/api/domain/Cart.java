package com.board.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart")
@Entity
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItemList = new ArrayList<>();


    public void addCartItem(CartItem cartItem) {
        cartItemList.add(cartItem);

        if(cartItem.getCart() != this) {
            cartItem.setCart(this);
        }
    }

}
