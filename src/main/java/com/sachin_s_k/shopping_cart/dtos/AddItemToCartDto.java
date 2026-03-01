package com.sachin_s_k.shopping_cart.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartDto {
    @NotNull
    private Long id;
}
