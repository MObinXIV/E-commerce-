package com.mobi.ecommerce.order;

import com.mobi.ecommerce.order_product.OrderProduct;
import com.mobi.ecommerce.order_product.OrderProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderProducts", target = "orderProducts")
    OrderResponse toOrderResponse(Order order);


    Order toOrder(OrderRequest orderRequest);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "price", target = "price")
    @Mapping(source="order.id",target = "orderId")
    @Mapping(source = "quantity", target = "quantity")
    OrderProductResponse toOrderProductResponse(OrderProduct orderProduct);
    List<OrderProductResponse> toOrderProductResponses(List<OrderProduct> orderProducts);
}
