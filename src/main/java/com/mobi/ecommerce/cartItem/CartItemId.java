package com.mobi.ecommerce.cartItem;

import com.mobi.ecommerce.order_product.OrderProductId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CartItemId {
    @Column(name = "product_id")
    private UUID productId;
    @Column(name="cart_id")
    private  UUID cartId;

    public CartItemId(UUID productId, UUID cartId) {
        this.productId = productId;
        this.cartId = cartId;
    }

    public CartItemId() {
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemId that = (CartItemId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(cartId, that.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, cartId);
    }
}
