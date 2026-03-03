package com.sachin_s_k.shopping_cart.dtos;

import com.sachin_s_k.shopping_cart.validations.LowerCase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    @Size(max=255, message = "Name at least less than 255 characters")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @LowerCase(message = "Email must be in lower case")
    private String email;
    @NotBlank(message = "Name is required")
@Size(min = 6,max=25,message = "password must be between 6-25 characters")
    private String password;
}
