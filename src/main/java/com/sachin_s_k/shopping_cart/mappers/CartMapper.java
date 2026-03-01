package com.sachin_s_k.shopping_cart.mappers;

import com.sachin_s_k.shopping_cart.dtos.CartDto;
import com.sachin_s_k.shopping_cart.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
     CartDto toDto(Cart cart);
}
