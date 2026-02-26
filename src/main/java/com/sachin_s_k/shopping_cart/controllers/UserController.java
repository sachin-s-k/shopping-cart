package com.sachin_s_k.shopping_cart.controllers;


import com.sachin_s_k.shopping_cart.dtos.UserDto;
import com.sachin_s_k.shopping_cart.entities.User;
import com.sachin_s_k.shopping_cart.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    @GetMapping
    public Iterable<UserDto> sayHello(){
        return  userRepository.findAll().stream().map(user -> new UserDto(user.getId(),user.getName(),user.getEmail())).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var user=userRepository.findById(id).orElse(null);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new UserDto(user.getId(),user.getEmail(),user.getName()),HttpStatus.OK);
    }

}