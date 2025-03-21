package com.mobi.ecommerce.order_product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OrderProductId {
    @Column(name = "product_id")
    private UUID productId;
    @Column(name = "order_id")
    private UUID orderId;

    public OrderProductId(UUID productId, UUID orderId) {
        this.productId = productId;
        this.orderId = orderId;
    }

    public OrderProductId() {
        
    }
    public UUID getProductId() {
        return productId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductId that = (OrderProductId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, orderId);
    }
}
