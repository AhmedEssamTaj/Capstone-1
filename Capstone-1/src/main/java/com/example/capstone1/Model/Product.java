package com.example.capstone1.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "id cannot be null")
    private String productId;

    @NotEmpty(message = "name cannot be null")
    @Size(min = 4, message = "name must be more then 3 length long")
    private String productName;

    @NotNull(message = "price cannot be null")
    @Positive(message = "price must be a positive number")
    private double productPrice;

    @NotEmpty(message = "category cannot be null")
    private String categoryId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Read-only in JSON
    private double averageRating = 0.0;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Read-only in JSON
    private int ratingCount = 0;

}
