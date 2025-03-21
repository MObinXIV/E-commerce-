package com.mobi.ecommerce.order;

import com.mobi.ecommerce.order_product.OrderProduct;
import com.mobi.ecommerce.order_product.OrderProductResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderResponse {
    private UUID id;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int shippingFee;
    private String phoneNumber;
    private List<OrderProductResponse> orderProducts;

    public OrderResponse(UUID id, OrderStatus orderStatus, PaymentMethod paymentMethod,
                         BigDecimal totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt,
                         int shippingFee, String phoneNumber, List<OrderProductResponse> orderProducts) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.shippingFee = shippingFee;
        this.phoneNumber = phoneNumber;
        this.orderProducts = orderProducts;
    }

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderStatus(),
                order.getPaymentMethod(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getLastModifiedDate(),
                order.getShippingFee(),
                order.getPhoneNumber(),
                order.getOrderProducts().stream()
                        .map(OrderProductResponse::fromEntity)
                        .collect(Collectors.toList()) // Convert orderProducts to DTOs
        );
    }

    public UUID getId() { return id; }
    public OrderStatus getOrderStatus() { return orderStatus; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public int getShippingFee() { return shippingFee; }
    public String getPhoneNumber() { return phoneNumber; }
    public List<OrderProductResponse> getOrderProducts() { return orderProducts; }
}
