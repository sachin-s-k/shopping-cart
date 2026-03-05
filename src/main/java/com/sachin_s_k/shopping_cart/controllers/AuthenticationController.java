package com.sachin_s_k.shopping_cart.controllers;


import com.sachin_s_k.shopping_cart.config.JwtConfig;
import com.sachin_s_k.shopping_cart.dtos.JwtResponse;
import com.sachin_s_k.shopping_cart.dtos.LoginUserRequest;
import com.sachin_s_k.shopping_cart.dtos.UserDto;
import com.sachin_s_k.shopping_cart.mappers.UserMapper;
import com.sachin_s_k.shopping_cart.repositories.UserRepository;
import com.sachin_s_k.shopping_cart.services.AuthenticationService;
import com.sachin_s_k.shopping_cart.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private  final JwtConfig jwtConfig;
     @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginUserRequest userRequest,
                                             HttpServletResponse response){

authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(),userRequest.getPassword()));
var user= userRepository.findByEmail(userRequest.getEmail()).orElseThrow();
var accessToken=jwtService.generateAccessToken(user);
var refreshToken=jwtService.generateRefreshToken(user);
var cookie= new Cookie("refreshToken",refreshToken.toString());
cookie.setHttpOnly(true);
cookie.setSecure(false);
cookie.setPath("/") ;
cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());// 7 dayscookie.setHttpOnly(true);
response.addCookie(cookie);
return ResponseEntity.ok(new JwtResponse(accessToken.toString()));

    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialException(){
         return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
//     @PostMapping("/validate")
//    public boolean validateToken(@RequestHeader("Authorization") String authHeader){
//         System.out.println("validate called");
//         var token= authHeader.replace("Bearer ","");
//
//        return jwtService.validateToken(token );
//
//    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        var authentication=SecurityContextHolder.getContext().getAuthentication();
        var userId=(Long) authentication.getPrincipal();
        var user= userRepository.findById(userId).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        var userDto=userMapper.toDto(user);
        return ResponseEntity.ok(userDto);

    }


    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken){

         var jwt = jwtService.parseToken(refreshToken);
        if(jwt==null||jwt.isExpired()){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var userId= jwt.getUserId();
        var user=userRepository.findById(userId).orElseThrow();
        var accessToken= jwtService.generateAccessToken(user);
        return   ResponseEntity.ok(new JwtResponse(accessToken.toString()));


    }


}
