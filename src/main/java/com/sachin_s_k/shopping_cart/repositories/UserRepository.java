package com.sachin_s_k.shopping_cart.repositories;

import com.sachin_s_k.shopping_cart.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

 Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
