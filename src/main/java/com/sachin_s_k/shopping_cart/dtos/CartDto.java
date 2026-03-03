package com.sachin_s_k.shopping_cart.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;
    private List<CartItemDto> cartItemList=new ArrayList<>();

    private BigDecimal totalPrice=BigDecimal.ZERO;
}
