package com.mobi.ecommerce.cart;

import com.mobi.ecommerce.cartItem.CartItemRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CartRequest {
    @NotNull(message = "Cart must contain at least one product")
    @Size(min = 1, message = "At least one cart item is required")
    private List<CartItemRequest> cartItems;

    public CartRequest(List<CartItemRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public CartRequest() {
    }

    public List<CartItemRequest> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemRequest> cartItems) {
        this.cartItems = cartItems;
    }
}
