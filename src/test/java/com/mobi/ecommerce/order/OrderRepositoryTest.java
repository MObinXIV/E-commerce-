package com.mobi.ecommerce.order;

import com.github.javafaker.Faker;
import com.mobi.ecommerce.AbstractTestContainers;
import com.mobi.ecommerce.product.Product;
import com.mobi.ecommerce.user.Gender;
import com.mobi.ecommerce.user.User;
import com.mobi.ecommerce.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest extends AbstractTestContainers {

    private final Faker FAKER= new Faker();

    @Autowired
    private  OrderRepository underTest;

    @Autowired

    private UserRepository userRepository;

    private User testUser;


    @BeforeEach
    void setUp() {

        testUser = new User(
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.internet().safeEmailAddress(),
                FAKER.internet().password(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                false,
                true,
                FAKER.phoneNumber().subscriberNumber(10),
                Gender.MALE,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        userRepository.save(testUser);
        underTest.deleteAll();

    }

    @Test
    void findByUser() {
        // Given
        String address= FAKER.address().fullAddress();
        BigDecimal totalPrice = BigDecimal.valueOf(FAKER.number().randomDouble(2, 50, 100));
        Float shippingFee = totalPrice
                .multiply(BigDecimal.valueOf(0.10))
                .setScale(0, BigDecimal.ROUND_UP)
                .floatValue();
        Order order = new Order(
                OrderStatus.PENDING,
                PaymentMethod.CASH_ON_DELIVERY,
                totalPrice,
                LocalDateTime.now(),
                LocalDateTime.now(),
                shippingFee,
                testUser.getPhoneNumber(),
                address,
               false,
                testUser,
                new ArrayList<>()
        );
        underTest.save(order);
        // When
        List<Order> actual= underTest.findByUser(testUser);
        // Then
        assertThat(actual)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .extracting(Order::getUser,Order::getShippingAddress, Order::getTotalPrice, Order::getShippingFee)
                .containsExactly(testUser, address, totalPrice, shippingFee);
    }

    @Test
    void findByIdAndUserId() {
        // Given
        String address = FAKER.address().fullAddress();
        BigDecimal totalPrice = BigDecimal.valueOf(FAKER.number().randomDouble(2,50,100));
        Float shippingFee = totalPrice.multiply(BigDecimal.valueOf(.10)).setScale(0, BigDecimal.ROUND_UP)
                .floatValue();
        Order order = new Order(
                OrderStatus.PENDING,
                PaymentMethod.CASH_ON_DELIVERY,
                totalPrice,
                LocalDateTime.now(),
                LocalDateTime.now(),
                shippingFee,
                testUser.getPhoneNumber(),
                address,
                false,
                testUser,
                new ArrayList<>()
        );
        underTest.save(order);
        // When
        var actual = underTest.findByIdAndUserId(order.getId(),testUser.getId());
        // Then
        assertThat(actual).isPresent().get()
                .extracting(Order::getId, Order::getUser)
                .containsExactly(order.getId(),testUser);
    }
}