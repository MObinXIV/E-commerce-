package com.mobi.ecommerce.order;

import com.mobi.ecommerce.order_product.OrderProductRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public class OrderRequest {

    @NotNull(message = "Products list cannot be empty")
    @Size(min = 1, message = "At least one product must be ordered")
    private List<OrderProductRequest> products; // List of ordered products

    private String phoneNumber;
    private String shippingAddress;
    public OrderRequest() {}

    public OrderRequest(List<OrderProductRequest> products,
                        Integer shippingFee, String phoneNumber, String shippingAddress) {
        this.products = products;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
    }
    public List<OrderProductRequest> getProducts() { return products; }
    public void setProducts(List<OrderProductRequest> products) { this.products = products; }


    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
