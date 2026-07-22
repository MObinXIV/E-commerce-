package com.mobi.ecommerce.cartItem;

import com.mobi.ecommerce.cart.Cart;
import com.mobi.ecommerce.product.Product;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity(name = "CartItem")
@Table(name="cart_item")
@EntityListeners(AuditingEntityListener.class)
public class CartItem {

        @EmbeddedId
        private CartItemId cartItemId;

        @ManyToOne
        @MapsId("productId")
        @JoinColumn(name="product_id")
        private Product product;

        @ManyToOne
        @MapsId("cartId")
        @JoinColumn(name="cart_id")
        private Cart cart;

        @Column(name = "quantity")
        private  Integer quantity;
     @Column(name = "price_at_add_time", nullable = false, precision = 10, scale = 2)
        private BigDecimal priceAtAddition;
        @CreatedDate
        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;
        @LastModifiedDate
        @Column(name = "updated_at", insertable = false)
        private LocalDateTime updatedAt;



    public CartItem(CartItemId cartItemId, Product product, Cart cart, Integer quantity, LocalDateTime createdAt, LocalDateTime updatedAt, BigDecimal priceAtAddition) {
        this.cartItemId = cartItemId;
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.priceAtAddition = priceAtAddition;
    }

    public CartItem() {
    }

    public CartItemId getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(CartItemId cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getPriceAtAddition() {
        return priceAtAddition;
    }

    public void setPriceAtAddition(BigDecimal priceAtAddition) {
        this.priceAtAddition = priceAtAddition;
    }
}
