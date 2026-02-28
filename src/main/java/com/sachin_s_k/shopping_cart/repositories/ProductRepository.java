package com.sachin_s_k.shopping_cart.repositories;

import com.sachin_s_k.shopping_cart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryId(Byte id);
}
