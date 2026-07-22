package com.mobi.ecommerce.cartItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CartItemResponse {
    private UUID productId;
    private String productName;
    private Integer quantity;


    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
