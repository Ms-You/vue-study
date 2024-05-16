package com.board.api.repository;

import com.board.api.domain.Member;
import com.board.api.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value =
            "select distinct o " +
            "from Order o " +
            "join fetch o.orderItemList " +
            "where o.member = :member")
    List<Order> findByMemberWithOrderItems(@Param(value = "member") Member member);
}
