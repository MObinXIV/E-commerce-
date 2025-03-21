package com.mobi.ecommerce.product;

import com.mobi.ecommerce.security.SecurityUtils;
import com.mobi.ecommerce.user.User;
import com.mobi.ecommerce.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static ch.qos.logback.core.util.AggregationType.NOT_FOUND;

@Service
public class ProductService {
    private  final  ProductRepository productRepository;
//    private  final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    public ProductService(ProductRepository productRepository,  SecurityUtils securityUtils) {
        this.productRepository = productRepository;
//        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }

    // Get the currently authenticated user from the SecurityContext
//    private User getAuthenticatedUser() {
//        // Retrieves the currently authenticated user's email from the security context
//        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//
//// Finds the user by email in the user repository, or throws an unauthorized exception if not found
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
//    }
    public Product createProduct(ProductRequest productRequest) {
        User user = securityUtils.getAuthenticatedUser(); // Assuming this fetches the current authenticated user
        Optional<Product> existingProduct = productRepository.findByUserAndProductName(user, productRequest.getProductName());

        if (existingProduct.isPresent()) {

            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already exists, update stock instead.");
        }

        Product product = new Product();
        product.setProductName(productRequest.getProductName());  // FIXED
        product.setProduct_price(productRequest.getProductPrice()); // FIXED
        product.setProductDescription(productRequest.getProductDescription()); // FIXED
        product.setStock(productRequest.getStock());
        product.setUser(user);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        user.addProduct(product);

        return productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products= productRepository.findAll();
        return products.stream().map(product -> new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProduct_price(),
                product.getStock(),
                product.getCreatedAt()
        )).collect(Collectors.toList());
    }

    @Transactional
    public Product updateProduct(UUID productId,ProductRequest productRequest){
        User user = securityUtils.getAuthenticatedUser();

        Product product = productRepository.findById(productId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        // Ensure the authenticated user is the owner of the product
        if(!product.getUser().getId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own products");
        if (productRequest.getProductName()!=null)
            product.setProductName(productRequest.getProductName());
        if (productRequest.getProductPrice()!=null)
            product.setProduct_price(productRequest.getProductPrice());
        if (productRequest.getProductDescription()!=null)
            product.setProductDescription(productRequest.getProductDescription());
        if (productRequest.getStock() != null)
            product.setStock(productRequest.getStock());
        product.setUpdatedAt(LocalDateTime.now()); // Update timestamp

        return product;

    }

    public Product getProductById(UUID productId) {
        User user = securityUtils.getAuthenticatedUser();
        return productRepository.findByIdAndUserId(productId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }


    public void deleteProduct(UUID productId){
        User user = securityUtils.getAuthenticatedUser();
        Product existingProduct = productRepository.findByIdAndUserId(productId,user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productRepository.delete(existingProduct);
    }

}
