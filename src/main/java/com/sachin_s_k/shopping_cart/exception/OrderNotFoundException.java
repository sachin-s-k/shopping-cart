package com.sachin_s_k.shopping_cart.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(){
        super("Order not found exception");
    }

}
