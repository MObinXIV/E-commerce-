package com.mobi.ecommerce.product;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private  final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = productService.createProduct(request);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Product> updateUser(
            @PathVariable UUID productId,
            @RequestBody ProductRequest productRequest
            ){
        return ResponseEntity.ok(productService.updateProduct(productId,productRequest));
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
         productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
