package com.example.capstone1.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "id cannot be null")
    private String userId;

    @NotEmpty(message = "username cannot be null")
    @Size(min = 5,message = "username must be at least 5 characters")
    private String userName;

    @NotEmpty(message = "password cannot be empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$")
    @Size(min = 7, message = "password length must be more then 6 ")
    private String password;

    @NotEmpty(message = "email cannot be null")
    @Email(message = "Email must follow a valid email format")
    private String email;

    @NotEmpty(message = "role cannot be empty")
    @Pattern(regexp = "^(Admin|Customer)$",message = "role can only be either Customer or Admin")
    private String role;

    @NotNull(message = "balance cannot be null")
    @Positive(message = "balance must be positive!")
    private double balance;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ArrayList<Product> cart = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ArrayList<Product> history = new ArrayList<>();





}
