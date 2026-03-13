package com.sachin_s_k.shopping_cart.services;

import com.sachin_s_k.shopping_cart.dtos.CheckoutRequest;
import com.sachin_s_k.shopping_cart.dtos.CheckoutResponse;
import com.sachin_s_k.shopping_cart.entities.Order;
import com.sachin_s_k.shopping_cart.entities.PaymentStatus;
import com.sachin_s_k.shopping_cart.exception.CartEmptyException;
import com.sachin_s_k.shopping_cart.exception.CartNotFoundException;
import com.sachin_s_k.shopping_cart.exception.PaymentException;
import com.sachin_s_k.shopping_cart.repositories.CartRepository;
import com.sachin_s_k.shopping_cart.repositories.OrderRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;
    @Value("${websiteUrl}")
    private  String  websiteUrl;

    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart=cartRepository.getCartWithItems(request.getCartId()).orElse(null);

        if(cart==null){

   throw  new CartNotFoundException();

        }
        if(cart.isEmpty()){

throw  new CartEmptyException();
        }

        var order= Order.fromCart(cart,authService.getCurrentUser());



   try
   {
       orderRepository.save(order);



   var session=paymentGateway.createCheckoutsession(order);
       return  new CheckoutResponse(order.getId(),session.getCheckoutUrl());


   }catch (PaymentException Ex){
orderRepository.delete(order);
throw  Ex;
   }
    }

    public void handleWebhookEvent(WebhookRequest request){
      paymentGateway.parseWebhookEvent(request).ifPresent(paymentResult1 -> {
           var order= orderRepository.findById(Long.valueOf(paymentResult1.getOrderId())).orElseThrow();
           order.setOrderStatus(paymentResult1.getPaymentStatus());
           orderRepository.save(order);
       });



    }
}
