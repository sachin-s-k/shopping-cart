package com.sachin_s_k.shopping_cart.repositories;

import com.sachin_s_k.shopping_cart.entities.Order;
import com.sachin_s_k.shopping_cart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
