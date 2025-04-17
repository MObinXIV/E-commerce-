package com.mobi.ecommerce.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "user.id", target = "userId")
    ProductResponse toProductResponse(Product product);
}
