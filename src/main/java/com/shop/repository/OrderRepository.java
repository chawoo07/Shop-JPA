package com.shop.repository;

import com.shop.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o " +
     "where o.member.email = :email " +
     "order by o.orderDate desc"
    )
    List<Order> findOrders(@Param("email") String email, Pageable pageable);    //現在ログインしたユザーの注文データを条件に合わせて照会

    @Query("select count(o) from Order o " +
     "where o.member.email = :email"
    )
    Long countOrder(@Param("email") String email);                              //現在ログインした会員の注文数を照会

}
