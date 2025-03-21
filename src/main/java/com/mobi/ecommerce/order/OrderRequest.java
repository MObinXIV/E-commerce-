package com.mobi.ecommerce.order;

import java.util.UUID;

public class OrderRequest {

    private UUID productId;
    private int quantity;
    private PaymentMethod paymentMethod;
    private Integer shippingFee;
    private String phoneNumber;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Integer shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public OrderRequest(UUID productId, int quantity, PaymentMethod paymentMethod, Integer shippingFee, String phoneNumber) {
        this.productId = productId;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
        this.shippingFee = shippingFee;
        this.phoneNumber = phoneNumber;
    }
}
