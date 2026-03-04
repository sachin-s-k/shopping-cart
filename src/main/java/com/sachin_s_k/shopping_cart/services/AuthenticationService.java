package com.sachin_s_k.shopping_cart.services;

import com.sachin_s_k.shopping_cart.exception.InvalidCredentialException;
import com.sachin_s_k.shopping_cart.repositories.AuthenticationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;

    public void authenticateUser(String email,String password){
       var user =authenticationRepository.findByEmail(email).orElse(null);
       if(user==null){

throw  new InvalidCredentialException("Invalid email or password");
       }
if(!passwordEncoder.matches(password,user.getPassword())){
    throw  new InvalidCredentialException("Invalid email or password");

}

    }
}
