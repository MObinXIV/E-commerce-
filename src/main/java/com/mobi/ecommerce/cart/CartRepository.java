package com.mobi.ecommerce.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Boolean existsByUserId(UUID userid);
    Optional<Cart> findByUserId(UUID userId);

}
