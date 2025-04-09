package com.mobi.ecommerce.product;

import com.github.javafaker.Faker;
import com.mobi.ecommerce.security.SecurityUtils;
import com.mobi.ecommerce.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private ProductService underTest;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private SecurityUtils securityUtils;
    @Mock
    private   ProductMapper productMapper;
    private final static Faker FAKER = new Faker();

    @BeforeEach
    void setUp() {
        underTest = new ProductService(productRepository,securityUtils,productMapper);
    }

    @Test
    void createProduct() {
        // Given


        // When

        // Then

    }

    @Test
    void getAllProducts() {
        // When
        underTest.getAllProducts();
        // Then
        verify(productRepository).findAll();

    }

    @Test
    void updateProduct() {
        // Given


        // When

        // Then

    }

    @Test
    void CanGetProductById() {
        // Given
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
         Product product = new Product();
         product.setId(productId);
         product.setUser(user);
        ProductResponse productResponse = new ProductResponse(); // you can customize this if needed

        when(securityUtils.getAuthenticatedUser()).thenReturn(user);
        when(productRepository.findByIdAndUserId(productId,userId)).thenReturn(Optional.of(product));
        when(productMapper.toProductResponse(product)).thenReturn(productResponse);

        // When
        ProductResponse actual = underTest.getProductById(productId);
        // Then
        assertThat(actual).isEqualTo(productResponse);

        verify(securityUtils).getAuthenticatedUser();
        verify(productRepository).findByIdAndUserId(productId, userId);
        verify(productMapper).toProductResponse(product);
    }

    @Test
    void deleteProduct() {
        // Given


        // When

        // Then

    }
}