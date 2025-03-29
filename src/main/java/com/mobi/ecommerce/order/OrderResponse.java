package com.mobi.ecommerce.order;

import com.mobi.ecommerce.order_product.OrderProductResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderResponse {
    private UUID id;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedDate;
    private Integer shippingFee;
    private String phoneNumber;
    private UUID userId; // Added
    private List<OrderProductResponse> orderProducts;
    private boolean locked; // Added
    private String shippingAddress;
    public void setId(UUID id) {
        this.id = id;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setShippingFee(Integer shippingFee) {
        this.shippingFee = shippingFee;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setOrderProducts(List<OrderProductResponse> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }


    public OrderResponse(UUID id, OrderStatus orderStatus, PaymentMethod paymentMethod,
                         BigDecimal totalPrice, LocalDateTime createdAt, LocalDateTime lastModifiedDate,
                         int shippingFee, String phoneNumber, UUID userId,
                         List<OrderProductResponse> orderProducts, boolean locked, String shippingAddress) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.lastModifiedDate = lastModifiedDate;
        this.shippingFee = shippingFee;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.orderProducts = orderProducts != null ? orderProducts : new ArrayList<>(); // Null safety
        this.locked = locked;
        this.shippingAddress = shippingAddress;
    }

    // Default constructor for safety
    public OrderResponse() {
        this.orderProducts = new ArrayList<>();
    }

    public UUID getId() { return id; }
    public OrderStatus getOrderStatus() { return orderStatus; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return lastModifiedDate; }
    public Integer getShippingFee() { return shippingFee; }
    public String getPhoneNumber() { return phoneNumber; }
    public UUID getUserId() { return userId; }
    public List<OrderProductResponse> getOrderProducts() { return orderProducts; }
    public boolean isLocked() { return locked; }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}