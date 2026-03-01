package com.sachin_s_k.shopping_cart.controllers;

import com.sachin_s_k.shopping_cart.dtos.AddItemToCartDto;
import com.sachin_s_k.shopping_cart.dtos.CartDto;
import com.sachin_s_k.shopping_cart.dtos.CartItemDto;
import com.sachin_s_k.shopping_cart.entities.Cart;
import com.sachin_s_k.shopping_cart.entities.CartItem;
import com.sachin_s_k.shopping_cart.mappers.CartMapper;
import com.sachin_s_k.shopping_cart.repositories.CartRepository;
import com.sachin_s_k.shopping_cart.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @PostMapping
public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder){
     var cart= new Cart();

     cartRepository.save(cart);
     var cartDto= cartMapper.toDto(cart);
    var uri= uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
     return   ResponseEntity.created(uri).body(cartDto);
}

@PostMapping("/{cartId}/items")
public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId,@RequestBody AddItemToCartDto request){
    System.out.println(cartId+"=====>");
        var cart= cartRepository.findById(cartId).orElse(null);
    System.out.println("++++"+cart);
        if(cart==null){
            return  ResponseEntity.notFound().build();
        }
 var product = productRepository.findById(request.getId()).orElse(null);
        if(product==null){
            return ResponseEntity.badRequest().build();
        }

        var cartItem= cart.getCartItems().stream().filter(cartItemOne -> cartItemOne.getProduct().getId().equals(request.getId())).findFirst().orElse(null);
        if (cartItem!=null){

            cartItem.setQuantity(cartItem.getQuantity()+1);

        }else{
            System.out.println("entered");
            var newCartItem= new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setCart(cart);
            newCartItem.setQuantity(1);
            newCartItem.setCart(cart);
            cart.getCartItems().add(newCartItem);
        }
        cartRepository.save(cart);
        return  ResponseEntity.ok(null);

}
}
