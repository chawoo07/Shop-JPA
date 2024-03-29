package com.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue
    @JoinColumn(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //注文価格

    private int count;  //数量

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
       orderItem.setOrderPrice(item.getPrice());

       item.removeStock(count);
       return orderItem;        //注文の分だけ減少
    }

    public int getTotalPrice(){
        return orderPrice*count;    //注文数＊価格
    }

    public void cancel(){
        this.getItem().addStock(count);
    }

}
