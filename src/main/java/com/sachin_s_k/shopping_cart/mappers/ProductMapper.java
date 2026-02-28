package com.sachin_s_k.shopping_cart.mappers;

import com.sachin_s_k.shopping_cart.dtos.ProductDto;
import com.sachin_s_k.shopping_cart.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source ="category.id",target = "categoryId")
    ProductDto toDto(Product product);
}
