//package com.mobi.ecommerce.product;
//
//import com.github.javafaker.Faker;
//import com.mobi.ecommerce.exception.NotFound;
//import com.mobi.ecommerce.security.SecurityUtils;
//import com.mobi.ecommerce.user.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.then;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//class ProductServiceTest {
//    private ProductService underTest;
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private SecurityUtils securityUtils;
//    @Mock
//    private   ProductMapper productMapper;
//    private  final static Faker FAKER = new Faker();
//    private User user;
//    @BeforeEach
//    void setUp() {
//        underTest = new ProductService(productRepository,securityUtils,productMapper);
//        UUID userId= UUID.randomUUID();
//        user = new User();
//        user.setId(userId);
//    }
//
//    @Test
//    void createProduct() {
//        // Given
//        String description = FAKER.lorem().sentence();
//        BigDecimal price = BigDecimal.valueOf(FAKER.number().randomDouble(2, 10, 100));
//        int stock = FAKER.number().numberBetween(1, 100);
//
//        user.setProducts(new ArrayList<>());
//
//        String productName = FAKER.commerce().productName();
//        ProductRequest request = new ProductRequest(productName, description, price, stock);
//        // Mock the behavior of securityUtils to return the current authenticated user (the one created above).
//        when(securityUtils.getAuthenticatedUser()).thenReturn(user);
//        // Mock the behavior of productRepository to return an empty Optional, indicating no existing product with the same name.
//        when(productRepository.findByUserAndProductName(user,productName)).thenReturn(Optional.empty());
//        // Create a new Product object with the data from the ProductRequest and the current timestamp.
//
//        Product savedProduct  = new Product(
//                productName,
//                description,
//                price,
//                stock,
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                user,
//                new ArrayList<>()
//        );
//        savedProduct.setId(UUID.randomUUID());
//        // Mock the behavior of productRepository.save() to return the saved product object.
//        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
//        ProductResponse productResponse = new ProductResponse();
//        // Mock the behavior of productMapper to return the mocked ProductResponse when mapping the Product object.
//        when(productMapper.toProductResponse(any(Product.class))).thenReturn(productResponse);
//        // When
//        ProductResponse actual = underTest.createProduct(request);
//        // Then
//        // Use ArgumentCaptor to capture the Product object passed to productRepository.save().
//
//        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
//
//        // Verify that productRepository.save() was called and capture the passed Product object.
//        verify(productRepository).save(productArgumentCaptor.capture());
//
//        // Retrieve the captured product
//        Product capturedProduct = productArgumentCaptor.getValue();
//
//        // Assert that the captured Product object has the correct values (but id should be null, as it's not yet saved).
//        assertThat(capturedProduct.getId()).isNull();
//        assertThat(capturedProduct.getProductName()).isEqualTo(productName);
//        assertThat(capturedProduct.getProductDescription()).isEqualTo(description);
//        assertThat(capturedProduct.getProduct_price()).isEqualTo(price);
//        assertThat(capturedProduct.getStock()).isEqualTo(stock);
//        assertThat(capturedProduct.getUser()).isEqualTo(user);
//
//        // Assert that the returned ProductResponse is the one we mocked earlier.
//        assertThat(actual).isEqualTo(productResponse);
//
//        // Verify that all the necessary methods were called (securityUtils, productRepository, and productMapper).
//        verify(securityUtils).getAuthenticatedUser();
//        verify(productRepository).findByUserAndProductName(user, productName);
//        verify(productMapper).toProductResponse(savedProduct);
//
//    }
//    @Test
//    void ExistErrorProduct(){
//        // Given
//        UUID userId = UUID.randomUUID();
//        User user = new User();
//        user.setId(userId);
//
//        String productName = FAKER.commerce().productName();
//        String description = FAKER.lorem().sentence();
//        BigDecimal price = BigDecimal.valueOf(FAKER.number().randomDouble(2, 10, 100));
//        int stock = FAKER.number().numberBetween(1, 100);
//        ProductRequest request = new ProductRequest(productName, description, price, stock);
//
//        Product existingProduct = new Product(
//                productName,
//                description,
//                price,
//                stock,
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                user,
//                new ArrayList<>()
//        );
//
//        // Mock authenticated user
//        when(securityUtils.getAuthenticatedUser()).thenReturn(user);
//
//        // Mock the product with the same name
//
//        when(productRepository.findByUserAndProductName(user,productName)).thenReturn(Optional.of(existingProduct));
//
//        // When & Then
//        assertThatThrownBy(()->underTest.createProduct(request))
//                .isInstanceOf(NotFound.class)
//                .hasMessage("Product already exists, update stock instead.");
//
//        // Ensure mapper and save were not called
//        verify(productMapper, never()).toProductResponse(any());
//        verify(productRepository, never()).save(any());
//
//    }
//    @Test
//    void getAllProducts() {
//        // When
//        underTest.getAllProducts();
//        // Then
//        verify(productRepository).findAll();
//
//    }
//
//    @Test
//    void canUpdateAllProduct() {
//        // Given
//        UUID productId = UUID.randomUUID(); // Random UUID for the product to be updated
//
//        // New values to update the product with
//        String newName = FAKER.commerce().productName();
//        String newDescription = FAKER.lorem().sentence();
//        BigDecimal newPrice = BigDecimal.valueOf(FAKER.number().randomDouble(2, 10, 100));
//        int newStock = FAKER.number().numberBetween(1, 100);
//
//        // Create a request with new data
//        ProductUpdateRequest request = new ProductUpdateRequest(newName, newDescription, newPrice, newStock);
//
//        // Create an existing product to simulate what's in the DB
//        Product existingProduct = new Product();
//        existingProduct.setId(productId);
//        existingProduct.setProductName("Old Name");
//        existingProduct.setProductDescription("Old Desc");
//        existingProduct.setProduct_price(BigDecimal.valueOf(50));
//        existingProduct.setStock(10);
//        existingProduct.setUser(user);
//
//        // Expected response after update (dummy/mock object)
//        ProductResponse expectedResponse = new ProductResponse();
//
//        // Mock behavior: return authenticated user
//        when(securityUtils.getAuthenticatedUser()).thenReturn(user);
//
//        // Mock behavior: return the existing product from DB
//        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
//
//        // Mock behavior: convert the updated product into a response
//        when(productMapper.toProductResponse(existingProduct)).thenReturn(expectedResponse);
//
//        // When: Call the actual method under test
//        underTest.updateProduct(productId, request);
//
//        // Then: Capture the Product object passed to the mapper
//        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
//        verify(productMapper).toProductResponse(productArgumentCaptor.capture());
//
//        // Extract captured product
//        Product capturedProduct = productArgumentCaptor.getValue();
//
//        // Print for debug (optional)
//        System.out.println(capturedProduct.getProductName());
//
//        // Assert that the captured product has updated values
//        assertThat(capturedProduct.getProductName()).isEqualTo(newName);
//        assertThat(capturedProduct.getProductDescription()).isEqualTo(newDescription);
//        assertThat(capturedProduct.getProduct_price()).isEqualTo(newPrice);
//        assertThat(capturedProduct.getStock()).isEqualTo(newStock);
//        assertThat(capturedProduct.getUser()).isEqualTo(user);
//        assertThat(capturedProduct.getUpdatedAt()).isNotNull(); // Should have updated timestamp
//
//        // Verify that required methods were called
//        verify(securityUtils).getAuthenticatedUser();
//        verify(productRepository).findById(productId);
//        verify(productMapper).toProductResponse(existingProduct);
//    }
//
//    @Test
//    void willThrowIfUserDoesNotOwnProduct() {
//        // Given
//        UUID productId = UUID.randomUUID();
//        User otherUser = new User(); otherUser.setId(UUID.randomUUID());
//        User loggedInUser = new User(); loggedInUser.setId(UUID.randomUUID());
//
//        Product product = new Product();
//        product.setId(productId);
//        product.setUser(otherUser);
//
//        ProductUpdateRequest request = new ProductUpdateRequest("New", null, null, null);
//
//        when(securityUtils.getAuthenticatedUser()).thenReturn(loggedInUser);
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        // When / Then
//        assertThatThrownBy(() -> underTest.updateProduct(productId, request))
//                .isInstanceOf(NotFound.class)
//                .hasMessage("You can only update your own products");
//    }
//
//    @Test
//    void CanGetProductById() {
//        // Given
//        UUID productId = UUID.randomUUID();
//         Product product = new Product();
//         product.setId(productId);
//         product.setUser(user);
//        ProductResponse productResponse = new ProductResponse();
//        // test the user & get him
//        when(securityUtils.getAuthenticatedUser()).thenReturn(user);
//        when(productRepository.findByIdAndUserId(productId,user.getId())).thenReturn(Optional.of(product));
//        when(productMapper.toProductResponse(product)).thenReturn(productResponse);
//
//        // When
//        ProductResponse actual = underTest.getProductById(productId);
//        // Then
//        assertThat(actual).isEqualTo(productResponse);
//
//        verify(securityUtils).getAuthenticatedUser();
//        verify(productRepository).findByIdAndUserId(productId, user.getId());
//        verify(productMapper).toProductResponse(product);
//    }
//    @Test
//    void WillThrowEmptyErrorProductById(){
//        UUID productId = UUID.randomUUID();
//
//
//        when(securityUtils.getAuthenticatedUser()).thenReturn(user);
//        when(productRepository.findByIdAndUserId(productId,user.getId())).thenReturn(Optional.empty());
//        assertThatThrownBy(()->underTest.getProductById(productId))
//                .isInstanceOf(NotFound.class)
//                .hasMessage("Product not found");
//
//        then(securityUtils).should().getAuthenticatedUser();
//        then(productRepository).should().findByIdAndUserId(productId, user.getId());
//        then(productMapper).shouldHaveNoInteractions();
//    }
//
//
//    @Test
//    void deleteProduct() {
//        // Given
//        UUID productId= UUID.randomUUID();
//        Product product = new Product();
//        product.setId(productId);
//        product.setUser(user);
//
//        when(securityUtils.getAuthenticatedUser()).thenReturn(user);
//        when(productRepository.findByIdAndUserId(productId,user.getId())).thenReturn(Optional.of(product));
//        // When
//        underTest.deleteProduct(productId);
//        // Then
//        verify(productRepository).delete(product);
//    }
//    @Test
//    void willThrowWhenProductNotFoundOnDelete() {
//        // Given
//        UUID productId = UUID.randomUUID();
//
//        // Mock current authenticated user
//        when(securityUtils.getAuthenticatedUser()).thenReturn(user);
//
//        // Mock product not found
//        when(productRepository.findByIdAndUserId(productId, user.getId())).thenReturn(Optional.empty());
//
//        // When & Then
//        assertThatThrownBy(() -> underTest.deleteProduct(productId))
//                .isInstanceOf(NotFound.class)
//                .hasMessageContaining("Product not found");
//
//        // Make sure delete is never called
//        verify(productRepository, never()).delete(any());
//    }
//
//}