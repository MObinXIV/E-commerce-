package com.mobi.ecommerce.product;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity(name = "Product")
@Table (name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private UUID id;
    @Column(
            name = "product_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private  String product_name;
    @Column(
            name = "descrpition",
            nullable = false,
            columnDefinition = "TEXT"
    )
    String productDescription;
    @Column(
            name = "price",
            nullable = false,
//             precision = 10,  // Total number of digits
//            scale = 2
            columnDefinition = "DECIMAL(10,2)" // equal of the 2 previous statements
    )
    BigDecimal product_price;
    @Column(
            name = "stock",
            nullable = false
    )
    private int stock; // Available quantity in inventory
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
