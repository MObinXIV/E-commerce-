package com.mobi.ecommerce.order_product;

import com.mobi.ecommerce.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,OrderProductId> {
    List<OrderProduct> findByProduct(Product product);

}
