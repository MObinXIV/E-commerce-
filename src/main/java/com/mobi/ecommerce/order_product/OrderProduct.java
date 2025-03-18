//package com.mobi.ecommerce.order_product;
//
//import com.mobi.ecommerce.order.Order;
//import com.mobi.ecommerce.product.Product;
//import jakarta.persistence.*;
//
//import java.math.BigInteger;
//import java.time.LocalDateTime;
//
//@Entity(name = "OrderProduct")
//@Table (name = "order_product")
//public class OrderProduct {
//    @EmbeddedId
//    private OrderProductId id;
//
//    @ManyToOne
//    @MapsId("productId")
//    @JoinColumn (name="product_id",foreignKey = @ForeignKey(name ="orderProduct_product_id_fk" ))
//    private Product product;
//
//    @ManyToOne
//    @MapsId("orderId")
//    @JoinColumn (name="order_id",foreignKey = @ForeignKey(name ="OrderProduct_order_id_fk" ))
//    private Order order;
//    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
//    private LocalDateTime createdAt;
//
//    @Column(name = "price" , nullable = false)
//    private BigInteger price;
//
//    public BigInteger getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigInteger price) {
//        this.price = price;
//    }
//
//    public OrderProduct(OrderProductId id, Product product, Order order, LocalDateTime createdAt, BigInteger price) {
//        this.id = id;
//        this.product = product;
//        this.order = order;
//        this.createdAt = createdAt;
//        this.price = price;
//    }
//
//    public OrderProduct() {
//    }
//
//    public OrderProductId getId() {
//        return id;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public Order getOrder() {
//        return order;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setId(OrderProductId id) {
//        this.id = id;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//}
