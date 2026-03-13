package com.sachin_s_k.shopping_cart.controllers;

import com.sachin_s_k.shopping_cart.dtos.CheckoutRequest;
import com.sachin_s_k.shopping_cart.dtos.CheckoutResponse;
import com.sachin_s_k.shopping_cart.dtos.ErrorDto;

import com.sachin_s_k.shopping_cart.exception.CartEmptyException;
import com.sachin_s_k.shopping_cart.exception.CartNotFoundException;

import com.sachin_s_k.shopping_cart.exception.PaymentException;
import com.sachin_s_k.shopping_cart.repositories.OrderRepository;
import com.sachin_s_k.shopping_cart.services.CheckoutService;
import com.sachin_s_k.shopping_cart.services.WebhookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;


    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {

        return checkoutService.checkout(request);

    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Error creating checkout session"));

    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex) {

        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));

    }

    @PostMapping("/webhook")
    public void handleWebhook(@RequestHeader Map<String, String> headers,
                                              @RequestBody String payload) {


        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));

    }

}
