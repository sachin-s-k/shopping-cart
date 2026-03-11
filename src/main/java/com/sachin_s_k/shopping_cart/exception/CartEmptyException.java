package com.sachin_s_k.shopping_cart.exception;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException(){
        super("Cart is Empty");
    }

}
