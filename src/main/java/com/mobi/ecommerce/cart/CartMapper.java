package com.mobi.ecommerce.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "id", target = "cartId")
    CartResponse toCartResponse(Cart cart);
}
