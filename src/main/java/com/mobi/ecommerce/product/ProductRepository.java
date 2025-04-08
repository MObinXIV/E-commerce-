package com.mobi.ecommerce.product;

import com.mobi.ecommerce.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional <Product> findByIdAndUserId(UUID productId, UUID userId);
    Optional<Product> findByUserAndProductName(User user, String productName);
}
