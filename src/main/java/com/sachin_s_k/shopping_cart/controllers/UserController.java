package com.sachin_s_k.shopping_cart.controllers;


import com.sachin_s_k.shopping_cart.dtos.UserDto;
import com.sachin_s_k.shopping_cart.entities.User;
import com.sachin_s_k.shopping_cart.mappers.UserMapper;
import com.sachin_s_k.shopping_cart.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController

@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @GetMapping
    public Iterable<UserDto> sayHello(
            @RequestHeader(name="x-auth-token",required = false) String authToken,@RequestParam(required = false,defaultValue = "",name = "sort") String sortBy){
        System.out.println(authToken+"=======>");

        if(!Set.of("name","email").contains(sortBy)){

            sortBy="name";
        }
        return  userRepository.findAll(Sort.by(sortBy)).stream().map(userMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var user=userRepository.findById(id).orElse(null);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.toDto(user),HttpStatus.OK);
    }

}