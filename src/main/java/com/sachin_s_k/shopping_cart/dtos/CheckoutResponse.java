package com.sachin_s_k.shopping_cart.dtos;

import lombok.Data;

@Data
public class CheckoutResponse {
    private Long orderId;

    public CheckoutResponse(Long orderId) {
        this.orderId = orderId;
    }
}
