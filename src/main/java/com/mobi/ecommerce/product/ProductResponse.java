package com.mobi.ecommerce.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductResponse {
     private UUID id;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private int stock;
    private LocalDateTime createdAt;

    public ProductResponse(UUID id, String productName, String productDescription, BigDecimal productPrice, int stock, LocalDateTime createdAt) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.stock = stock;

        this.createdAt = createdAt;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
