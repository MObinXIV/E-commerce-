package com.mobi.ecommerce.order;

import com.mobi.ecommerce.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
//    @Autowired
    private final OrderService orderService;
    private final ProductService productService;

    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }
    @PostMapping
//    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<OrderResponse>createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse orderResponse = orderService.createOrder(request);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllProducts(){
        return  ResponseEntity.ok(orderService.getAllOrders());
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID orderId){

        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable UUID orderId ,@Valid @RequestBody OrderRequest request){
        return  ResponseEntity.ok(orderService.updateOrder(orderId,request));
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }
    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<OrderResponse>> getOrdersForProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(orderService.getOrdersForProduct(productId));
    }

}
