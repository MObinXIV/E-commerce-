package com.mobi.ecommerce.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobi.ecommerce.order_product.OrderProduct;
import com.mobi.ecommerce.user.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
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
    private  String productName;

    @Column(
            name = "product_description",
            nullable = false,
            columnDefinition = "TEXT"
    )
  private  String productDescription;
    @Column(
            name = "price",
            nullable = false,
//             precision = 10,  // Total number of digits
//            scale = 2
            columnDefinition = "DECIMAL(10,2)" // equal of the 2 previous statements
    )
    private BigDecimal productPrice;
    @Column(
            name = "stock",
            nullable = false
    )
    private Integer stock; // Available quantity in inventory
    @CreatedDate
    @Column ( name = "createdAt", nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updatedAt", insertable = false)
    private LocalDateTime updatedAt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_product_fk"))
    private User user;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();



    public Product(UUID id, String productName, String productDescription, BigDecimal productPrice, Integer stock, LocalDateTime createdAt, LocalDateTime updatedAt, User user, List<OrderProduct> orderProducts) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.orderProducts = orderProducts;
    }

    public Product(String productName, String productDescription, BigDecimal productPrice, Integer stock, LocalDateTime createdAt, LocalDateTime updatedAt, User user, List<OrderProduct> orderProducts) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.orderProducts = orderProducts;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product() {
    }

    public UUID getId() {
        return id;
    }



    public String getProductDescription() {
        return productDescription;
    }



    public Integer getStock() {
        return stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    public void setId(UUID id) {
        this.id = id;
    }



    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }



    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    void addOrderProduct(OrderProduct orderProduct){
        if(orderProduct != null &&!orderProducts.contains(orderProduct)){
            orderProducts.add(orderProduct);
            orderProduct.setProduct(this);
        }
    }
    void removeOrderProduct(OrderProduct orderProduct){
        orderProducts.remove(orderProduct);
        orderProduct.setProduct(null);
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}
