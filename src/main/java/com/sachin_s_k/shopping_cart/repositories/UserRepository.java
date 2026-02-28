package com.sachin_s_k.shopping_cart.repositories;

import com.sachin_s_k.shopping_cart.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User,Long> {


    boolean existsByEmail(String email);
}
