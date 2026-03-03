package com.sachin_s_k.shopping_cart.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartDto {
    @NotNull(message = "id Cannot be null")
    private Long productId;
}
