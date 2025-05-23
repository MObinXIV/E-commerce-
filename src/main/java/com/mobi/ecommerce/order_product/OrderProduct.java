package com.mobi.ecommerce.order_product;

import com.mobi.ecommerce.order.Order;
import com.mobi.ecommerce.product.Product;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "OrderProduct")
@Table (name = "order_product")
public class OrderProduct {
    @EmbeddedId
    private OrderProductId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn (name="product_id",foreignKey = @ForeignKey(name ="orderProduct_product_id_fk" ))
    private Product product;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn (name="order_id",foreignKey = @ForeignKey(name ="OrderProduct_order_id_fk" ))
    private Order order;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime createdAt;
    @Column(name = "updatedAt", insertable = false)
    private LocalDateTime lastModifiedDate;
    @Column(name = "total_price" , nullable = false)
    private BigDecimal price;

    @Column(name = "quantity")
   private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderProduct(OrderProductId id, Product product, Order order, LocalDateTime createdAt, LocalDateTime lastModifiedDate, BigDecimal price , Integer quantity) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.createdAt = createdAt;
        this.lastModifiedDate = lastModifiedDate;
        this.price = price;  // ✅ Assign actual price
        this.quantity=quantity;
    }


    public OrderProduct() {
    }

    public OrderProductId getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(OrderProductId id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrder(Order order) {
        this.order = order;
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
}
