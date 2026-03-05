package com.sachin_s_k.shopping_cart.config;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String secret;
    private int accessTokenExpiration;
    private  int refreshTokenExpiration;
    public SecretKey generateSecretKey(){
        return  Keys.hmacShaKeyFor(secret.getBytes());
    }
}
