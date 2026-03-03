package com.sachin_s_k.shopping_cart.controllers;


import com.sachin_s_k.shopping_cart.dtos.ChangePasswordRequest;
import com.sachin_s_k.shopping_cart.dtos.RegisterUserRequest;
import com.sachin_s_k.shopping_cart.dtos.UpdateUserRequest;
import com.sachin_s_k.shopping_cart.dtos.UserDto;
import com.sachin_s_k.shopping_cart.entities.User;
import com.sachin_s_k.shopping_cart.mappers.UserMapper;
import com.sachin_s_k.shopping_cart.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController

@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
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
   @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterUserRequest  request, UriComponentsBuilder uriComponentsBuilder){

       if( userRepository.existsByEmail(request.getEmail())){
           return ResponseEntity.badRequest().body(
                   Map.of("email","email is already registered.")
           );
       }
        var user=  userMapper.toEntity(request);
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        var response = userRepository.save(user);
        var userDto= userMapper.toDto(user);
        var uri=uriComponentsBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
       return ResponseEntity.created(uri).body(userDto);

    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "userId") Long id, @RequestBody  UpdateUserRequest request){
        var user=userRepository.findById(id).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
     userMapper.update(request,user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        var user=userRepository.findById(id).orElse(null);
        if(user ==null){
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();

    }
     @PostMapping("/{userId}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable(name = "userId") Long id, @RequestBody ChangePasswordRequest changePasswordRequest){
          var user= userRepository.findById(id).orElse(null);
         System.out.println(user);
          if(user==null){
              return ResponseEntity.notFound().build();

          }

          if(!user.getPassword().equals(changePasswordRequest.getOldPassword())){
              return  ResponseEntity.badRequest().build();
          }
          
          user.setPassword(changePasswordRequest.getNewPassword());
          userRepository.save(user);
          return ResponseEntity.noContent().build();


    }


}