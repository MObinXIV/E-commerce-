package com.mobi.ecommerce.product;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRequest {
    @NonNull
    private String productName;
    @NonNull
    private String productDescription;
    private BigDecimal productPrice;
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
