package com.mobi.ecommerce.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobi.ecommerce.cartItem.CartItem;
import com.mobi.ecommerce.user.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Cart")
@Table(name="cart")
@EntityListeners(AuditingEntityListener.class)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",updatable = false)
    private UUID id;
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_cart_fk"))
    private User user;

    @OneToMany(mappedBy = "cart",orphanRemoval = true)
    private List<CartItem> cartItem = new ArrayList<>();

    public Cart() {
    }

    public Cart(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, User user, List<CartItem> cartItem) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.cartItem = cartItem;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<CartItem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem) {
        this.cartItem = cartItem;
    }
}
