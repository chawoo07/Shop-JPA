package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class OrderDto {

    @NotNull(message = "商品のIDは必須です。")
    public Long itemId;

    @Min(value = 1, message = "最小数は一個です。")
    @Max(value = 999, message = "最大数は999個です。")
    private int count;

}
