package com.sachin_s_k.shopping_cart.controllers;

import com.sachin_s_k.shopping_cart.dtos.ProductDto;
import com.sachin_s_k.shopping_cart.entities.Product;
import com.sachin_s_k.shopping_cart.mappers.ProductMapper;
import com.sachin_s_k.shopping_cart.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(name = "categoryId", required = false) Byte categoryId){
        List<Product> products;
    if(categoryId!=null){
       products= productRepository.findByCategoryId(categoryId);
    }else{
        products= productRepository.findAll();
    }
return products.stream().map(productMapper::toDto).toList();
    };
   @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){

       var product= productRepository.findById(id).orElse(null);

       if(product==null){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

       return new ResponseEntity<>(productMapper.toDto(product),HttpStatus.OK);

    }


}
