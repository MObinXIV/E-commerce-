package com.mobi.ecommerce.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobi.ecommerce.user.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity(name = "Order")
@Table (name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private  UUID id;
    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
    @CreatedDate
    @Column ( name = "createdAt", nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updatedAt", insertable = false)
    private LocalDateTime lastModifiedDate;
    @Column(name = "shipping_fee", nullable = false)
    private Integer shippingFee;

    @Column(name="phone_number" , nullable = false)
    private String phoneNumber;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_orders_fk"))
    private User user;


}
