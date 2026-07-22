package com.mobi.ecommerce.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem,CartItemId> {
    Optional<CartItem> findByCartIdAndProductId(UUID cartId, UUID productId);

}
