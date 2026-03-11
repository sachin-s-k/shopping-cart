package com.sachin_s_k.shopping_cart.services;

import com.sachin_s_k.shopping_cart.dtos.CheckoutRequest;
import com.sachin_s_k.shopping_cart.dtos.CheckoutResponse;
import com.sachin_s_k.shopping_cart.dtos.ErrorDto;
import com.sachin_s_k.shopping_cart.entities.Order;
import com.sachin_s_k.shopping_cart.exception.CartEmptyException;
import com.sachin_s_k.shopping_cart.exception.CartNotFoundException;
import com.sachin_s_k.shopping_cart.repositories.CartRepository;
import com.sachin_s_k.shopping_cart.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    public CheckoutResponse checkout(CheckoutRequest request){
        var cart=cartRepository.getCartWithItems(request.getCartId()).orElse(null);

        if(cart==null){

   throw      new CartNotFoundException();

        }
        if(cart.isEmpty()){

throw  new CartEmptyException();
        }

        var order= Order.fromCart(cart,authService.getCurrentUser());
        orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return  new CheckoutResponse(order.getId());

    }
}
