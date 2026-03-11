package com.sachin_s_k.shopping_cart.controllers;

import com.sachin_s_k.shopping_cart.dtos.CheckoutRequest;
import com.sachin_s_k.shopping_cart.dtos.CheckoutResponse;
import com.sachin_s_k.shopping_cart.dtos.ErrorDto;
import com.sachin_s_k.shopping_cart.entities.Order;
import com.sachin_s_k.shopping_cart.entities.OrderItem;
import com.sachin_s_k.shopping_cart.entities.OrderStatus;
import com.sachin_s_k.shopping_cart.exception.CartEmptyException;
import com.sachin_s_k.shopping_cart.exception.CartNotFoundException;
import com.sachin_s_k.shopping_cart.repositories.CartRepository;
import com.sachin_s_k.shopping_cart.repositories.OrderRepository;
import com.sachin_s_k.shopping_cart.services.AuthService;
import com.sachin_s_k.shopping_cart.services.CartService;
import com.sachin_s_k.shopping_cart.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request){
return  checkoutService.checkout(request);


    }
     @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){

        return  ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));

    }
}
