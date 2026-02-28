package com.sachin_s_k.shopping_cart.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ProductDto {
    private Long id;
    private  String name;
    private String description;
    private BigDecimal price;
    private   byte  categoryId;

}
