package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class CartItemDto {

    @NotNull(message = "商品IDは必須です。")
    private Long itemId;

    @Min(value = 1, message = "最小1個以上です。")
    private int count;

}
