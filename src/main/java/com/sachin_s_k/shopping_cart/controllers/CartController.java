package com.sachin_s_k.shopping_cart.controllers;

import com.sachin_s_k.shopping_cart.dtos.AddItemToCartDto;
import com.sachin_s_k.shopping_cart.dtos.CartDto;
import com.sachin_s_k.shopping_cart.dtos.CartItemDto;
import com.sachin_s_k.shopping_cart.dtos.UpdateCartItemRequest;
import com.sachin_s_k.shopping_cart.entities.Cart;
import com.sachin_s_k.shopping_cart.entities.CartItem;
import com.sachin_s_k.shopping_cart.exception.CartNotFoundException;
import com.sachin_s_k.shopping_cart.exception.ProductNotFoundException;
import com.sachin_s_k.shopping_cart.mappers.CartMapper;
import com.sachin_s_k.shopping_cart.repositories.CartRepository;
import com.sachin_s_k.shopping_cart.repositories.ProductRepository;
import com.sachin_s_k.shopping_cart.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder){
 var cartDto= cartService.createCart();
    var uri= uriBuilder.path("/carts/{id}").buildAndExpand(  cartService.createCart().getId()).toUri();
     return ResponseEntity.created(uri).body(cartDto);
}

@PostMapping("/{cartId}/items")
public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId,@Valid @RequestBody AddItemToCartDto request){
    var cartItemDto= cartService.addToCart(cartId,request.getProductId());
        return  ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);

}

@GetMapping("/{cartId}")
public CartDto getCart(@PathVariable UUID cartId){
        return cartService.findCart(cartId);
}


@PutMapping("/{cartId}/items/{productId}")
public CartItemDto updateItem(@PathVariable("cartId") UUID cartId, @PathVariable("productId") Long productId, @RequestBody UpdateCartItemRequest updateCartItemRequest){

return  cartService.updateCart(cartId,productId, updateCartItemRequest.getQuantity());


}

@DeleteMapping("/{cartId}/items/{productId}")
public ResponseEntity<?> removeItem(@PathVariable("cartId") UUID cartId, @PathVariable("productId") Long productId){
cartService.removeItem(cartId,productId);
        return  ResponseEntity.noContent().build();

}

@DeleteMapping("/{cartId}/items")
public ResponseEntity<Void> clearCart(@PathVariable  UUID cartId){

cartService.clearCart(cartId);
        return  ResponseEntity.noContent().build();


}

@ExceptionHandler(CartNotFoundException.class)
public ResponseEntity< Map<String,String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Cart not found"));

}

@ExceptionHandler(ProductNotFoundException.class)
public ResponseEntity<Map<String, String>> handleProductNotFound(){
        return ResponseEntity.status( HttpStatus.BAD_REQUEST).body(Map.of("error","Product not found in the cart."));
}

}
