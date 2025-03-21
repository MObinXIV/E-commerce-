package com.mobi.ecommerce.order_product;

import com.mobi.ecommerce.product.ProductResponse;
import java.math.BigDecimal;
import java.util.UUID;

public class OrderProductResponse {
    private UUID productId;
    private String productName;
    private int quantity;
    private BigDecimal price;

    public OrderProductResponse(UUID productId, String productName, int quantity, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    // This is a static factory method that converts an OrderProduct entity into an OrderProductResponse
    public static OrderProductResponse fromEntity(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getProduct().getId(),          // ✅ Extracts the product ID from the Product entity
                orderProduct.getProduct().getProductName(), // ✅ Extracts the product name from the Product entity
                orderProduct.getQuantity(),                 // ✅ Retrieves the quantity of this product in the order
                orderProduct.getPrice()                     // ✅ Retrieves the total price for this product in the order
        );
    }

    public UUID getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
}
