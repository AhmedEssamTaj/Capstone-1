package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "id cannot be null")
    private String merchantId;

    @NotEmpty(message = "name cannot be null")
    @Size(min = 4, message = "name must be more then 3 length long")
    private String merchantName;
}