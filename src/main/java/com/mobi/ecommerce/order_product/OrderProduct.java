package com.mobi.ecommerce.order_product;

import com.mobi.ecommerce.order.Order;
import com.mobi.ecommerce.product.Product;
import com.mobi.ecommerce.role.User_RoleId;
import jakarta.persistence.*;

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
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime createdAt;

}
