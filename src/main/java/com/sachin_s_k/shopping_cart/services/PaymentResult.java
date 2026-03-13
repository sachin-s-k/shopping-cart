package com.sachin_s_k.shopping_cart.services;

import com.sachin_s_k.shopping_cart.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymentStatus;

}
