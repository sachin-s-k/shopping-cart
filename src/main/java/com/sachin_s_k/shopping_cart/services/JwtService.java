package com.sachin_s_k.shopping_cart.services;

import com.sachin_s_k.shopping_cart.config.JwtConfig;
import com.sachin_s_k.shopping_cart.entities.Role;
import com.sachin_s_k.shopping_cart.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@AllArgsConstructor
@Service
public class JwtService  {
    private final JwtConfig jwtConfig;

    public Jwt generateAccessToken(User user){

        return  generateToken(user, jwtConfig.getAccessTokenExpiration());

    }

    public Jwt generateRefreshToken(User user){

        return  generateToken(user, jwtConfig.getRefreshTokenExpiration());

    }


    private Claims getClaims(String token){
   return Jwts.parser().verifyWith(jwtConfig.generateSecretKey())
                .build().parseSignedClaims(token).getPayload();
    }


    public Jwt generateToken(User user , int tokenExpiration){

        var claims=Jwts.
                claims().
                subject(user.getId().toString()).
                add("email",user.getEmail()).
                add("name",user.getName()).
                add("role",user.getRole()).
                        issuedAt( new Date()).
                        expiration(new Date(System.currentTimeMillis()+1000*tokenExpiration)).build();
        return new Jwt(claims,jwtConfig.generateSecretKey());
    }

    public Role getRoleFromToken (String token){
        return  Role.valueOf(getClaims(token).get("role",String.class));

    }
    public Jwt parseToken(String token){
        try {
            var claims=getClaims(token);
            return  new Jwt(claims, jwtConfig.generateSecretKey());
        }catch (JwtException e){
            return  null;

        }

    }
}
