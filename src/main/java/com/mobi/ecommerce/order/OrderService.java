package com.mobi.ecommerce.order;

import com.mobi.ecommerce.order_product.OrderProduct;
import com.mobi.ecommerce.order_product.OrderProductId;
import com.mobi.ecommerce.order_product.OrderProductRepository;
import com.mobi.ecommerce.product.Product;
import com.mobi.ecommerce.product.ProductRepository;
import com.mobi.ecommerce.security.SecurityUtils;
import com.mobi.ecommerce.user.User;
import com.mobi.ecommerce.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final SecurityUtils securityUtils;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderService(OrderRepository orderRepository, SecurityUtils securityUtils, ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.securityUtils = securityUtils;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }
    public OrderResponse createOrder(OrderRequest request) {
        User user = securityUtils.getAuthenticatedUser();
        Order order = new Order();
        order.setPaymentMethod(request.getPaymentMethod());
        order.setShippingFee(request.getShippingFee());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalPrice(BigDecimal.ZERO);
        order.setCreatedAt(LocalDateTime.now());
        order.setUser(user);
        user.addOrder(order);
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.fromEntity(savedOrder);
    }

//    @Transactional
//    public Order addProductToOrder(UUID orderId, UUID productId, int quantity){
//        User user = securityUtils.getAuthenticatedUser();
//        Order order= orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
//       // check the order if it's belong to the user
//       if(!order.getUser().equals(user))
//       {
//           throw new RuntimeException("Unauthorized to modify this order");
//       }
//        if (order.isLocked()) {
//            throw new RuntimeException("Order is locked and cannot be modified");
//        }
//        // Fetch the product from the repository
//        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
//        // decrease the product stock
//        product.setStock(product.getStock()-quantity);
//        productRepository.save(product);
//        OrderProductId orderProductId = new OrderProductId(productId, orderId);
//
//        // create order product entry
//        OrderProduct orderProduct= new OrderProduct();
//        orderProduct.setId(orderProductId);
//        orderProduct.setOrder(order);
//        orderProduct.setProduct(product);
//        orderProduct.setQuantity(quantity);
//        orderProduct.setPrice(product.getProduct_price().multiply(BigDecimal.valueOf(quantity)));
//        order.addOrderProduct(orderProduct);
//        orderProductRepository.save(orderProduct);
//        return orderRepository.save(order);
//    }

    @Transactional
    public OrderResponse addProductToOrder(UUID orderId, UUID productId, int quantity) {
        User user = securityUtils.getAuthenticatedUser();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().equals(user)) {
            throw new RuntimeException("Unauthorized to modify this order");
        }

        if (order.isLocked()) {
            throw new RuntimeException("Order is locked and cannot be modified");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        // Generate the composite Id
        OrderProductId orderProductId = new OrderProductId(productId, orderId);

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(orderProductId);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(quantity);
        orderProduct.setPrice(product.getProduct_price().multiply(BigDecimal.valueOf(quantity)));
        // add the product to the order
        order.addOrderProduct(orderProduct);
        // save it to
        orderProductRepository.save(orderProduct);
        // Convert from order to orderResponse
        return OrderResponse.fromEntity(orderRepository.save(order));
    }

//    @Transactional
//    public OrderResponse removeProductFromOrder(UUID orderId, UUID productId) {
//        User user = securityUtils.getAuthenticatedUser(); // Get the authenticated user
//
//        // Fetch the order and verify it belongs to the user
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        if (!order.getUser().equals(user)) {
//            throw new RuntimeException("Unauthorized to modify this order");
//        }
//
//        // Prevent modification if order is locked
//        if (order.isLocked()) {
//            throw new RuntimeException("Order is locked and cannot be modified");
//        }
//
//        // Find the OrderProduct entry in the order
//        OrderProduct orderProduct = order.getOrderProducts().stream()
//                .filter(op -> op.getProduct().getId().equals(productId))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Product not found in order"));
//
//        // Restore stock when removing the product
//        Product product = orderProduct.getProduct();
//        product.setStock(product.getStock() + orderProduct.getQuantity());
//        productRepository.save(product); // Save updated stock
//
//        // Remove the product from the order
//        order.removeOrderProduct(orderProduct);
//        orderProductRepository.delete(orderProduct); // Delete entry from DB
//
//        return OrderResponse.fromEntity(orderRepository.save(order)); // Convert to DTO and return updated order
//    }

//    @Transactional
//    public Order removeProductFromOrder(UUID orderId, UUID productId) {
//        User user = securityUtils.getAuthenticatedUser();
//
//        // Find the order and verify ownership
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        if (!order.getUser().equals(user)) {
//            throw new RuntimeException("Unauthorized to modify this order");
//        }
//
//        if (order.isLocked()) {
//            throw new RuntimeException("Order is locked and cannot be modified");
//        }
//
//        // Find the OrderProduct entry
//        Optional<OrderProduct> orderProductOptional = order.getOrderProducts()
//                .stream()
//                .filter(op -> op.getProduct().getId().equals(productId))
//                .findFirst();
//
//        if (orderProductOptional.isEmpty()) {
//            throw new RuntimeException("Product not found in order");
//        }
//
//        OrderProduct orderProduct = orderProductOptional.get();
//
//        // Restore stock
//        Product product = orderProduct.getProduct();
//        product.setStock(product.getStock() + orderProduct.getQuantity());
//        productRepository.save(product);
//
//        // Remove product from order
//        order.removeOrderProduct(orderProduct);
//        orderProductRepository.delete(orderProduct);
//
//        return orderRepository.save(order);
//    }
}
