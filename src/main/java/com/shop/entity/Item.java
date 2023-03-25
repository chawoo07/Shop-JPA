package com.shop.entity;


import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="item")
@Getter @Setter
@ToString
public class Item extends BaseEntity{

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

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;     //残りの在庫
        if(restStock<0){
            throw new OutOfStockException("商品の在庫がないです。　(現在在庫数： " + this.stockNumber + ")"); //例外
        }
        this.stockNumber = restStock;                       //注文の後、残りの在庫の数をアップデート
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

}
