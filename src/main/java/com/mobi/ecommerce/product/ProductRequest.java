package com.mobi.ecommerce.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class ProductRequest {
    @NotEmpty(message ="productName shouldn't be empty")
    private String productName;
    @NotEmpty(message ="product price shouldn't be empty")
    private String productDescription;
    @NotNull(message = "Product price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than 0")

    private BigDecimal productPrice;
    @NotNull(message = "Stock cannot be null")
    @PositiveOrZero(message = "Stock must be zero or a positive number")
    private Integer stock;
//    private UUID userId;

    public ProductRequest(String productName, String productDescription, BigDecimal productPrice, int stock) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.stock = stock;
    }

    public ProductRequest() {
    }


    public String getProductName() {
        return productName;
    }



    public String getProductDescription() {
        return productDescription;
    }


    public BigDecimal getProductPrice() {
        return productPrice;
    }



    public Integer getStock() {
        return stock;
    }


//    public UUID getUserId() {
//        return userId;
//    }
//
//    public void setUserId(UUID userId) {
//        this.userId = userId;
//    }
}
