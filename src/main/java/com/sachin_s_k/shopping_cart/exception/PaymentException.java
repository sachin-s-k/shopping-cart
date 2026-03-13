package com.sachin_s_k.shopping_cart.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentException extends RuntimeException {
    public PaymentException(String message){
        super(message);
    }
}
