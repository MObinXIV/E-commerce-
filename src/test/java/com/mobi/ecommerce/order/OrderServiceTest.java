package com.mobi.ecommerce.order;

import com.mobi.ecommerce.exception.NotFound;
import com.mobi.ecommerce.order_product.OrderProductRepository;
import com.mobi.ecommerce.product.ProductRepository;
import com.mobi.ecommerce.security.SecurityUtils;
import com.mobi.ecommerce.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

class OrderServiceTest {

    private OrderService underTest;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SecurityUtils securityUtils;

    @Mock
    private ProductRepository productRepository;

    @Mock

    private OrderProductRepository orderProductRepository;

    @Mock

    private  OrderMapper orderMapper;
     private User user;
    @BeforeEach
    void setUp() {
        underTest = new OrderService(orderRepository,securityUtils,productRepository,orderProductRepository,orderMapper);
        UUID userId= UUID.randomUUID();
        user = new User();
        user.setId(userId);

    }

    @Test
    void createOrder() {
        // Given

        // When

        // Then

    }

    @Test
    void getAllOrders() {
        //Given
        when(securityUtils.getAuthenticatedUser()).thenReturn(user);

        // When
        underTest.getAllOrders();
        // Then
        verify(orderRepository).findByUser(user);
    }

    @Test
    void canGetOrderById() {
        // Given
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now()); // necessary
        OrderResponse response= new OrderResponse();
        when(securityUtils.getAuthenticatedUser()).thenReturn(user);
        when(orderRepository.findByIdAndUserId(orderId,user.getId())).thenReturn(Optional.of(order));
        when(orderMapper.toOrderResponse(order)).thenReturn(response);
        // When
        OrderResponse actual = underTest.getOrderById(orderId);
        // Then
        assertThat(actual).isEqualTo(response);
        verify(securityUtils).getAuthenticatedUser();
        verify(orderRepository).findByIdAndUserId(orderId, user.getId());
        verify(orderMapper).toOrderResponse(order);
    }
    @Test
    void willThrowEmptyErrorOrderById(){
        UUID orderId = UUID.randomUUID();

        when(securityUtils.getAuthenticatedUser()).thenReturn(user);
        when(orderRepository.findByIdAndUserId(orderId,user.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.getOrderById(orderId))
                .isInstanceOf(NotFound.class)
                .hasMessage("order is not found");
    }
    @Test
    void updateOrder() {
        // Given


        // When

        // Then

    }

    @Test
    void deleteOrder() {
        // Given


        // When

        // Then

    }

    @Test
    void getOrdersForProduct() {
        // Given


        // When

        // Then

    }
}