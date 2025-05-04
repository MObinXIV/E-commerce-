package com.mobi.ecommerce.product;

import com.mobi.ecommerce.exception.NotFound;
import com.mobi.ecommerce.exception.RequestValidationException;
import com.mobi.ecommerce.security.SecurityUtils;
import com.mobi.ecommerce.user.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ProductService {
    private  final  ProductRepository productRepository;
    private final SecurityUtils securityUtils;
    private  final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, SecurityUtils securityUtils, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.securityUtils = securityUtils;
        this.productMapper = productMapper;

    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        User user = securityUtils.getAuthenticatedUser(); // Assuming this fetches the current authenticated user
        Optional<Product> existingProduct = productRepository.findByUserAndProductName(user, productRequest.getProductName());

        if (existingProduct.isPresent()) {

            throw new NotFound("Product already exists, update stock instead.");
        }

        Product product = new Product();
        product.setProductName(productRequest.getProductName());  // FIXED
        product.setProductPrice(productRequest.getProductPrice()); // FIXED
        product.setProductDescription(productRequest.getProductDescription()); // FIXED
        product.setStock(productRequest.getStock());
        product.setUser(user);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        user.addProduct(product);
        product =productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    public Page<ProductResponse> getAllProducts(int page,int size, String sortBy,String direction){
        Sort sort= Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Product> products= productRepository.findAll(pageable);

        return products.map(productMapper::toProductResponse);
    }

    @Transactional
    public ProductResponse updateProduct(UUID productId,ProductUpdateRequest productRequest){
        User user = securityUtils.getAuthenticatedUser();

        Product product = productRepository.findById(productId).orElseThrow(()-> new NotFound("Product not found"));
        boolean  changes = false;
        // Ensure the authenticated user is the owner of the product
        if(!product.getUser().getId().equals(user.getId()))
            throw new NotFound("You can only update your own products");
        if (productRequest.getProductName()!=null)
        {
            product.setProductName(productRequest.getProductName());
            changes = true;
        }
        if (productRequest.getProductPrice()!=null)
        {product.setProductPrice(productRequest.getProductPrice());
        changes=true;
        }
        if (productRequest.getProductDescription()!=null)
        {product.setProductDescription(productRequest.getProductDescription());
            changes=true;}
        if (productRequest.getStock() != null)
        {product.setStock(productRequest.getStock());
        changes=true;
        }
        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }
        product.setUpdatedAt(LocalDateTime.now()); // Update timestamp

        return productMapper.toProductResponse(product);
    }

    public ProductResponse getProductById(UUID productId) {
        User user = securityUtils.getAuthenticatedUser();
        Product product= productRepository.findByIdAndUserId(productId, user.getId())
                .orElseThrow(() -> new NotFound("Product not found"));
    return productMapper.toProductResponse(product);
    }


    public void deleteProduct(UUID productId){
        User user = securityUtils.getAuthenticatedUser();
        Product existingProduct = productRepository.findByIdAndUserId(productId,user.getId())
                .orElseThrow(() -> new NotFound( "Product not found"));
        productRepository.delete(existingProduct);
    }
}
