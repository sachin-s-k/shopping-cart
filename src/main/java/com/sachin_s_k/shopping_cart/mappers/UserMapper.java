package com.sachin_s_k.shopping_cart.mappers;


import com.sachin_s_k.shopping_cart.dtos.UserDto;
import com.sachin_s_k.shopping_cart.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto  toDto(User user);
}
