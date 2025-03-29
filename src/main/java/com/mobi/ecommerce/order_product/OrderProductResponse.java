package com.mobi.ecommerce.order_product;

import com.mobi.ecommerce.order_product.OrderProduct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderProductResponse {
    private UUID productId;
    private UUID orderId; // Added
    private String productName;
    private int quantity;
    private BigDecimal price;
    private LocalDateTime createdAt; // Added

    public OrderProductResponse(UUID productId, UUID orderId, String productName,
                                int quantity, BigDecimal price, LocalDateTime createdAt) {
        this.productId = productId;
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
    }

    public static OrderProductResponse fromEntity(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getId().getProductId(), // Updated to use composite ID
                orderProduct.getId().getOrderId(),   // Added from composite ID
                orderProduct.getProduct().getProductName(),
                orderProduct.getQuantity(),
                orderProduct.getPrice(),
                orderProduct.getCreatedAt()          // Added
        );
    }

    public UUID getProductId() { return productId; }
    public UUID getOrderId() { return orderId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}