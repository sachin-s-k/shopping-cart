package com.sachin_s_k.shopping_cart.repositories;

import com.sachin_s_k.shopping_cart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


}
