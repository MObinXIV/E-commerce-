package com.mobi.ecommerce.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobi.ecommerce.order_product.OrderProduct;
import com.mobi.ecommerce.user.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity(name = "Order")
@Table (name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private  UUID id;
    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice=BigDecimal.ZERO;
    @CreatedDate
    @Column ( name = "createdAt", nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updatedAt", insertable = false)
    private LocalDateTime lastModifiedDate;
    @Column(name = "shipping_fee", insertable = false)
    private Float shippingFee;

    @Column(name="phone_number" , nullable = false)
    private String phoneNumber;
    private String shippingAddress;
    private boolean isLocked;

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_order_fk"))
    private User user;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Order(UUID id, OrderStatus orderStatus, PaymentMethod paymentMethod, BigDecimal totalPrice, LocalDateTime createdAt, LocalDateTime lastModifiedDate, Float shippingFee, String phoneNumber, String shippingAddress, boolean isLocked, User user) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.lastModifiedDate = lastModifiedDate;
        this.shippingFee = shippingFee;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
        this.isLocked = isLocked;
        this.user = user;
    }

    public Order() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public Float getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Float shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    void addOrderProduct(OrderProduct orderProduct){
        if(orderProduct != null &&!orderProducts.contains(orderProduct)){
            orderProducts.add(orderProduct);
            orderProduct.setOrder(this);
            totalPrice = totalPrice.add(orderProduct.getPrice());

        }
    }
//    public boolean isLocked() {
//        return createdAt.plusDays(2).isBefore(LocalDateTime.now());
//    }
    void removeOrderProduct(OrderProduct orderProduct){
        orderProducts.remove(orderProduct);
        totalPrice = totalPrice.subtract(orderProduct.getPrice());

        orderProduct.setOrder(null);
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
