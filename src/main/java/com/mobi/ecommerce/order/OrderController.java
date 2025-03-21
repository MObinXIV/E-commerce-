package com.mobi.ecommerce.order;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    public ResponseEntity<OrderResponse>createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse orderResponse = orderService.createOrder(request);
        return ResponseEntity.ok(orderResponse);
    }
    @PostMapping("/{orderId}/product/{productId}")
    public ResponseEntity<OrderResponse> addProductToOrder(@PathVariable UUID orderId, @PathVariable UUID productId, @RequestParam int quantity) {
        OrderResponse updatedOrder = orderService.addProductToOrder(orderId, productId, quantity);
        return ResponseEntity.ok(updatedOrder);
    }
}
