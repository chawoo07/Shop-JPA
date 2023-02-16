package com.shop.entity;


import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //商品コード

    @Column(nullable = false,length = 50)
    private String itemNm; //商品名

    @Column(name = "price",nullable = false)
    private int price; //価格

    @Column(nullable = false)
    private int stockNumber; //ストック

    @Lob
    @Column(nullable = false)
    private String itemDetail; //商品の詳細説明

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //商品の販売状態

    private LocalDateTime regTime; //登録時間

    private LocalDateTime updateTime; //修正時間
}
