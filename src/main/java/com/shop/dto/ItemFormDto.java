package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "商品名を入力してください")
    private String itemNm;

    @NotNull(message = "価格を入力してください")
    private Integer price;

    @NotBlank(message = "説明を入力してください")
    private String itemDetail;

    @NotNull(message = "在庫数量を入力してください")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
    //商品貯蔵の後修正する時商品イメージ情報を貯蔵するList

    private List<Long> itemImgIds = new ArrayList<>();
    //商品のイメージIDを貯蔵するList。修正の時イメージIDを持つ

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }

}
