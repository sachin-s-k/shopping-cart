package com.sachin_s_k.shopping_cart.mappers;

import com.sachin_s_k.shopping_cart.dtos.OrderDto;
import com.sachin_s_k.shopping_cart.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "orderStatus", target = "status")
    OrderDto toDto(Order order);
}
