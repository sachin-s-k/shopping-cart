package com.sachin_s_k.shopping_cart.controllers;


import com.sachin_s_k.shopping_cart.dtos.JwtResponse;
import com.sachin_s_k.shopping_cart.dtos.LoginUserRequest;
import com.sachin_s_k.shopping_cart.services.AuthenticationService;
import com.sachin_s_k.shopping_cart.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
     @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginUserRequest userRequest){

authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(),userRequest.getPassword()));
var token=jwtService.generateToken(userRequest.getEmail());
return ResponseEntity.ok(new JwtResponse(token));

    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialException(){
         return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }


}
