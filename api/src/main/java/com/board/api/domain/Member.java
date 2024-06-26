package com.board.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 10)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();


    @Builder
    private Member(String email, String password, String nickName, RoleType role) {
        this.cart = new Cart();
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.role = role;
    }

}
