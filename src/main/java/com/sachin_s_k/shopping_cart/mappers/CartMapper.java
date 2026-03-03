package com.sachin_s_k.shopping_cart.mappers;

import com.sachin_s_k.shopping_cart.dtos.CartDto;
import com.sachin_s_k.shopping_cart.dtos.CartItemDto;
import com.sachin_s_k.shopping_cart.entities.Cart;
import com.sachin_s_k.shopping_cart.entities.CartItem;
import com.sachin_s_k.shopping_cart.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
     @Mapping(target = "cartItemList", source = "cartItems")
     @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
     CartDto toDto(Cart cart);
     @Mapping(target = "totalPrice",expression = "java(cartItem.getTotalPrice())")
     CartItemDto toDto (CartItem cartItem);
}
