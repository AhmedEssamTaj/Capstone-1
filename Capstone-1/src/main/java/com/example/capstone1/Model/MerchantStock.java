package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "merchant stock id cannot be null")
    private String merchantStockId;
    @NotEmpty(message = "product id cannot be null")
    private String productId;
    @NotEmpty(message = "merchant id cannot be null")
    private String merchantId;
    @NotNull(message = "stock cannot be null")
    @Min(value = 11, message = "stock must be more then 10")
    private int stock;



}
