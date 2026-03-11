package com.sachin_s_k.shopping_cart.services;

import com.sachin_s_k.shopping_cart.dtos.CartDto;
import com.sachin_s_k.shopping_cart.dtos.CartItemDto;
import com.sachin_s_k.shopping_cart.entities.Cart;
import com.sachin_s_k.shopping_cart.entities.CartItem;
import com.sachin_s_k.shopping_cart.exception.CartNotFoundException;
import com.sachin_s_k.shopping_cart.exception.ProductNotFoundException;
import com.sachin_s_k.shopping_cart.mappers.CartMapper;
import com.sachin_s_k.shopping_cart.repositories.CartRepository;
import com.sachin_s_k.shopping_cart.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService  {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartDto createCart(){
        System.out.println("called");
        var cart= new Cart();

        cartRepository.save(cart);
        var cartDto= cartMapper.toDto(cart);
        return  cartDto;
    }

    public CartItemDto addToCart(UUID cartId, Long productId){
        var cart= cartRepository.getCartWithItems(cartId).orElse(null);
        System.out.println(cart+"caarResponse");
        if(cart==null){
           throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        System.out.println(product+"======>");

        if(product==null){
throw  new ProductNotFoundException();
        }

        var cartItem= cart.addItem(product);
        cartRepository.save(cart);
        System.out.println(cartItem+"cartItemmmmm");
        return   cartMapper.toDto(cartItem);
    }

    public CartDto findCart(UUID cartId){
        var cart= cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null){
      throw new CartNotFoundException();
        }

        return cartMapper.toDto(cart);

    }
    public CartItemDto updateCart(UUID cartId, Long productId,Integer quantity){
        var cart= cartRepository.findById(cartId).orElse(null);
        if(cart==null){
            throw  new CartNotFoundException();
        }
        var cartItem= cart.getItem(productId);

        if(cartItem==null){
     throw  new ProductNotFoundException();
        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return  cartMapper.toDto(cartItem);
    }
    public void removeItem(UUID cartId, Long productId){
        var cart= cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null) {
           throw new CartNotFoundException();
        };
        cart.removeItem(productId);

        cartRepository.save(cart);


    }

    public void clearCart(UUID cartId){
        var cart=cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null){
throw new CartNotFoundException();
        }

        cart.clearItems();
        cartRepository.save(cart);


    }
}
