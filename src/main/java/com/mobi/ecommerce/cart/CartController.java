package com.mobi.ecommerce.cart;

import com.mobi.ecommerce.cartItem.CartItemRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartResponse> createCart(){
        return ResponseEntity.ok(cartService.createCart());
    }
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<CartResponse> getCart(){
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItemsToCart(@Valid @RequestBody CartItemRequest request){
        return ResponseEntity.ok(cartService.addItemsToCart(request));
    }

    @DeleteMapping("/items/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItemFromCart(
            @PathVariable UUID productId
    ) {
        cartService.removeItemFromCart(productId);
    }
}
