package com.shop.dto;


import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    private String searchDateType;              //現在時間と商品登録日比較して商品データを紹介
                                                //all:商品登録日全体
                                                //1d:最近一日間の登録された商品
                                                //1w:最近一週間の間の登録された商品
                                                //1m:最近一ヶ月間の登録された商品
                                                //6m:最近半年の登録された商品
    private ItemSellStatus searchSellstatus;    //商品の販売の状態を基準に商品データを紹介

    private String searchBy;                    //商品を紹介するときにどんな

    private String searchQuery = "";            //紹介する検索語を貯蔵する変数

}
