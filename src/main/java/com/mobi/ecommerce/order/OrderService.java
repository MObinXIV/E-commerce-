package com.mobi.ecommerce.order;

import com.mobi.ecommerce.exception.NotFound;
import com.mobi.ecommerce.order_product.OrderProduct;
import com.mobi.ecommerce.order_product.OrderProductId;
import com.mobi.ecommerce.order_product.OrderProductRepository;
import com.mobi.ecommerce.order_product.OrderProductRequest;
import com.mobi.ecommerce.product.Product;
import com.mobi.ecommerce.product.ProductRepository;
import com.mobi.ecommerce.product.ProductRequest;
import com.mobi.ecommerce.security.SecurityUtils;
import com.mobi.ecommerce.user.User;
import com.mobi.ecommerce.user.UserRepository;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final SecurityUtils securityUtils;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final  OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, SecurityUtils securityUtils, ProductRepository productRepository, OrderProductRepository orderProductRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.securityUtils = securityUtils;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
        this.orderMapper = orderMapper;
    }


    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = securityUtils.getAuthenticatedUser();
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderProduct> orderProducts = new ArrayList<>();

        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setLastModifiedDate(LocalDateTime.now());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setShippingAddress(request.getShippingAddress());
        order.setLocked(false);
        // Save the order first to generate an ID
        order = orderRepository.save(order);

        // Process each product
        for (var productRequest : request.getProducts()) {
            // get the product using bridge table
            Product product = productRepository.findById(productRequest.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            if (product.getStock() < productRequest.getQuantity()) {
                throw new NotFound("Insufficient stock for: " + product.getProductName());
            }

            // update stock and save product
            product.setStock(product.getStock() - productRequest.getQuantity());
            productRepository.save(product);

            // Calculate total price
            totalPrice = totalPrice.add(product.getProductPrice().multiply(BigDecimal.valueOf(productRequest.getQuantity())));

            // Save order-product new relation
            OrderProduct orderProduct = new OrderProduct(
                    new OrderProductId(order.getId(), product.getId()),
                    product,
                    order,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    product.getProductPrice().multiply(BigDecimal.valueOf(productRequest.getQuantity())),
                    productRequest.getQuantity()

            );
            orderProduct = orderProductRepository.save(orderProduct);
            orderProducts.add(orderProduct);
        }
        Float shippingFee=  totalPrice.multiply(BigDecimal.valueOf(0.10))
                .setScale(0, BigDecimal.ROUND_UP).floatValue();
        order.setShippingFee(shippingFee);
        // Set final total price and update order
        order.setOrderProducts(orderProducts);

        order.setTotalPrice(totalPrice.add(BigDecimal.valueOf(shippingFee)));

        order.setShippingFee(shippingFee);
        user.addOrder(order);
        return orderMapper.toOrderResponse(order);
    }
    private void applyLock(Order order){
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);

        if(order.getCreatedAt().isBefore(twoDaysAgo)&&!order.isLocked())
            order.setLocked(true);
            order.setOrderStatus(OrderStatus.SHIPPED);
            orderRepository.save(order);
    }
    public List<OrderResponse> getAllOrders(){
        User user = securityUtils.getAuthenticatedUser();
        List<Order> orders = orderRepository.findByUser(user);
        orders.forEach(this::applyLock);

        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }
    public  OrderResponse getOrderById(UUID id ){
        User user = securityUtils.getAuthenticatedUser();
        Order order= orderRepository.findByIdAndUserId(id,user.getId()).orElseThrow(()->new NotFound("order is not found"));
        applyLock(order);
        return orderMapper.toOrderResponse(order);
    }

    @Transactional
    OrderResponse updateOrder(UUID orderId,OrderRequest request){
        // Get the authenticated user
        User user = securityUtils.getAuthenticatedUser();

        // Find the order by ID and check if it belongs to the user
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (order.isLocked()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is locked and cannot be modified.");
        }
        boolean isModified=false;
        if(request.getPhoneNumber()!=null&& !request.getPhoneNumber().equals(order.getPhoneNumber())){
            order.setPhoneNumber(request.getPhoneNumber());
            isModified = true;
        }
        if (request.getShippingAddress() != null && !request.getShippingAddress().equals(order.getShippingAddress())) {
            order.setShippingAddress(request.getShippingAddress());
            isModified = true;
        }
        // ✅ Retrieve updated product list from the request
        List<OrderProductRequest> updatedProducts = request.getProducts();

        if(updatedProducts!=null && !updatedProducts.isEmpty()){
            List<OrderProduct> orderProducts = order.getOrderProducts(); // Get current products in order
            // loop throw updated products
            for(OrderProductRequest productRequest:updatedProducts){
                UUID productId = productRequest.getProductId(); // Get product ID from the update request
                // check if the product is existing in the current
                Optional<OrderProduct> existingOrderProduct = orderProducts.stream()
                        .filter(op->op.getProduct().getId().equals(productId))
                        .findFirst();
                // see if the product in the list
                if(existingOrderProduct.isPresent()){
                    OrderProduct orderProduct = existingOrderProduct.get();

                    // if the quantity is 0 we remove the product from the order
                    if(productRequest.getQuantity()==0){
                        orderProducts.remove(orderProduct);
                        isModified=true;
                    }
                    // ✅ If the quantity is different, update it

                    else if (orderProduct.getQuantity() != productRequest.getQuantity()){
                        int quantityDiff = productRequest.getQuantity() - orderProduct.getQuantity(); // Calculate difference
                        Product product = orderProduct.getProduct(); // Get the associated product
                        // ❌ If increasing quantity, ensure sufficient stock is available
                        if (quantityDiff > 0 && product.getStock() < quantityDiff) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "Insufficient stock for: " + product.getProductName());
                        }
                        // ✅ Adjust stock based on the quantity difference
                        product.setStock(product.getStock()-quantityDiff);
                        productRepository.save(product);
                        // update order product
                        orderProduct.setQuantity(productRequest.getQuantity());
                        orderProduct.setPrice(product.getProductPrice().multiply(BigDecimal.valueOf(productRequest.getQuantity())));
                        orderProduct.setLastModifiedDate(LocalDateTime.now());
                        isModified = true;
                    }
                }else {
                    // ❌ Prevent adding new products to an existing order
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add new products to an existing order");
                }
            }
        }
        if (!isModified) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No changes detected in the update request");
        }
        // ✅ Recalculate total price based on updated products
        BigDecimal totalPrice = order.getOrderProducts().stream()
                .map(OrderProduct::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all product prices
        // ✅ Calculate shipping fee as 10% of total price, rounded up
        float shippingFee = totalPrice.multiply(BigDecimal.valueOf(0.10))
                .setScale(0, BigDecimal.ROUND_UP).floatValue();
        // ✅ Update order total price and shipping fee
        order.setTotalPrice(totalPrice.add(BigDecimal.valueOf(shippingFee)));
        order.setShippingFee(shippingFee);
        order.setLastModifiedDate(LocalDateTime.now());

        // ✅ Save updated order to database
        orderRepository.save(order);

        // ✅ Return the updated order response
        return orderMapper.toOrderResponse(order);
    }

    @Transactional
    public  void deleteOrder(UUID orderId){
        User user =  securityUtils.getAuthenticatedUser();
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        // ❌ Prevent deletion if order is locked or shipped
        if (order.isLocked() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete a locked or shipped order");
        }
        for (OrderProduct orderProduct:order.getOrderProducts()){
            // get the product from orderProduct
            Product product = orderProduct.getProduct();
            product.setStock(product.getStock()+orderProduct.getQuantity());
            productRepository.save(product);
        }
        orderProductRepository.deleteAll(order.getOrderProducts());
        orderRepository.delete(order); // Remove order

    }
    public List<OrderResponse> getOrdersForProduct(UUID productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        // Get all orders containing this product
        List<OrderProduct> orderProducts = orderProductRepository.findByProduct(product);
        return orderProducts.stream()
                .map(orderProduct -> orderMapper.toOrderResponse(orderProduct.getOrder()))
                .toList();
    }
}
