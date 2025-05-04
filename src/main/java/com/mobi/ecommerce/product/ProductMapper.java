package com.mobi.ecommerce.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "user.id", target = "userId")
//    @Mapping(source = "product.productPrice" ,target = "productPrice")
    ProductResponse toProductResponse(Product product);
}
