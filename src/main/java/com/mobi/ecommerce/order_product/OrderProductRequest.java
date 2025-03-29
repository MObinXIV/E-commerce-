package com.mobi.ecommerce.order_product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class OrderProductRequest {

    @NotNull(message = "Product ID cannot be null")
    private UUID productId; // ID of the product being ordered

    @NotNull(message = "Order ID cannot be null")
    private UUID orderId; // ID of the order

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity; // Number of items being ordered

    @NotNull(message = "Price cannot be null")
    private BigDecimal price; // Total price for this product

    public OrderProductRequest() {}

    public OrderProductRequest(UUID productId, UUID orderId, int quantity, BigDecimal price) {
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
