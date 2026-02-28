package com.sachin_s_k.shopping_cart.controllers;

import com.sachin_s_k.shopping_cart.dtos.ProductDto;
import com.sachin_s_k.shopping_cart.entities.Product;
import com.sachin_s_k.shopping_cart.mappers.ProductMapper;
import com.sachin_s_k.shopping_cart.mappers.UserMapper;
import com.sachin_s_k.shopping_cart.repositories.CategoryRepository;
import com.sachin_s_k.shopping_cart.repositories.ProductRepository;
import com.sachin_s_k.shopping_cart.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

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

    @PostMapping
public ResponseEntity<ProductDto>  createProduct(@RequestBody ProductDto productData, UriComponentsBuilder uriComponentsBuilder){
        var category= categoryRepository.findById(productData.getCategoryId()).orElse(null);
        System.out.println(category+"======>");
        if(category==null){
            return ResponseEntity.badRequest().build();
        }
       var product=productMapper.toEntity(productData);
       product.setCategory(category);
       var response=  productRepository.save(product);
       var productDto=productMapper.toDto(product);
       var uri=uriComponentsBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();

       return ResponseEntity.created(uri).body(productDto);
}

@PutMapping("/{productId}")
public  ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "productId") Long id,@RequestBody ProductDto productData){

       var category= categoryRepository.findById(productData.getCategoryId()).orElse(null);
       if(category==null){
           return  ResponseEntity.notFound().build();
       }


       var product= productRepository.findById(id).orElse(null);
       if(product==null){
           return ResponseEntity.notFound().build();
       }
       productMapper.update(productData,product);
       product.setCategory(category);
       productRepository.save(product);
       productData.setId(product.getId());

       return ResponseEntity.ok(productData);

}

@DeleteMapping("/{productId}")

public ResponseEntity<Void> deleteProduct(@PathVariable(name = "productId") Long id){
       var product= productRepository.findById(id).orElse(null);
       if(product==null){
            return ResponseEntity.notFound().build();
       }


       productRepository.delete(product);

       return ResponseEntity.noContent().build();

}

}
