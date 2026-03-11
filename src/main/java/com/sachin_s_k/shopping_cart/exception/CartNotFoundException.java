package com.sachin_s_k.shopping_cart.exception;

public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(){
        super("Cart not found exception");
    }
}
