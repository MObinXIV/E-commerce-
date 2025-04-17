//package com.mobi.ecommerce.product;
//
//import com.github.javafaker.Faker;
//import com.mobi.ecommerce.AbstractTestContainers;
//import com.mobi.ecommerce.user.Gender;
//import com.mobi.ecommerce.user.User;
//import com.mobi.ecommerce.user.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.ApplicationContext;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class ProductRepositoryTest extends AbstractTestContainers {
//    private  final static Faker FAKER = new Faker();
//
//    @Autowired
//    private  ProductRepository underTest;
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    private User testUser;
//
//    @BeforeEach
//    void setUp() {
//        // Given: Set up a user before each test
//        testUser = new User(
//                FAKER.name().firstName(),
//                FAKER.name().lastName(),
//                FAKER.internet().safeEmailAddress(),
//                FAKER.internet().password(),
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                false,
//                true,
//                FAKER.phoneNumber().subscriberNumber(10),
//                Gender.MALE,
//                new ArrayList<>(),
//                new ArrayList<>(),
//                new ArrayList<>()
//        );
//        userRepository.save(testUser);
//
//        underTest.deleteAll();
//        System.out.println(applicationContext.getBeanDefinitionCount());
//    }
//
//    @Test
//    void findByIdAndUserId() {
//        // Given:Create and save a product for the testUser
//        String productName = FAKER.commerce().productName();
//
//        Product product = new Product(
//                productName,
//                FAKER.lorem().sentence(),
//                BigDecimal.valueOf(FAKER.number().randomDouble(2, 10, 100)),  // Random price
//                FAKER.number().numberBetween(1, 100),  // Random stock quantity
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                testUser,
//                new ArrayList<>()
//        );
//
//        underTest.save(product);
//
//        // When
//        var actual = underTest.findByIdAndUserId(product.getId(),testUser.getId());
//        // Then
//        assertThat(actual).isPresent().get()
//                .extracting(Product::getId, Product::getUser)
//                .containsExactly(product.getId(), testUser);
//    }
//
//    @Test
//    void findByUserAndProductName() {
//        // Given
//        String productName = FAKER.commerce().productName();
//        Product product = new Product(
//                productName,
//                FAKER.lorem().sentence(),
//                BigDecimal.valueOf(FAKER.number().randomDouble(2, 10, 100)),  // Random price
//                FAKER.number().numberBetween(1, 100),  // Random stock quantity
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                testUser,
//                new ArrayList<>()
//        );
//
//        underTest.save(product);
//
//        // When
//        var actual = underTest.findByUserAndProductName(testUser,productName);
//        // Then
//
// // Assert that the product was found and its name and user match the expected values:
//// - isPresent(): ensures the Optional<Product> is not empty
//// - get(): retrieves the actual Product object
//// - extracting(...): pulls specific fields (productName and user) from the Product
//// - containsExactly(...): verifies that the extracted fields match the expected values in order
//
//        assertThat(actual).isPresent().get()
//                .extracting(Product::getProductName,Product::getUser)
//                .containsExactly(productName,testUser);
//
//    }
//}