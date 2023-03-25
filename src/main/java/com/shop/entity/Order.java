package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders") //もうorderというキーワードがあるのでorders
@Getter @Setter
public class Order extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;          //一人のアカウントでいくつも注文することができるのでManyToOne

    private LocalDateTime orderDate;    //注文時間

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    //注文状態

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            orphanRemoval = true)   //孤児オブジェクトを消す

    //注文商品Entityと１：多マッピングする　外来キー(order_item)テーブルにあるので連関関係のマスターはOrderItemのEntity
    //Order Entityがマスターではないので"mappedBy属性で連関関係を設定
    //属性の値に"order"を与えた理由がOrderItemにあるOrderによって管理される意味
    private List<OrderItem> orderItems = new ArrayList<>();
    //一つの注文が幾つの注文商品を持つのでListを使ってマッピング

    public void addOrderItem(OrderItem orderItem){  //orderItemsに注文の情報を与える orderオブジェクトのorderItemsに追加
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        order.setMember(member);                    //注文した会員の情報をセットする
        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());    //現在時間を注文時間に
        return order;
    }

    public int getTotalPrice(){                     //総額
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

}
