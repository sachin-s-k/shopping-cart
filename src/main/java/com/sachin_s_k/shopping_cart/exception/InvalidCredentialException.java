package com.sachin_s_k.shopping_cart.exception;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message) {
        super(message);
    }

  public InvalidCredentialException() {
            super("Invalid email or password");
        }
    }

