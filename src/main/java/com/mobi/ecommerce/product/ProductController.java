package com.mobi.ecommerce.product;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private  final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam (defaultValue = "DESC") String direction
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, direction));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateOrder(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductUpdateRequest productRequest
            ){
        return ResponseEntity.ok(productService.updateProduct(productId,productRequest));
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID productId) {

        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
         productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
