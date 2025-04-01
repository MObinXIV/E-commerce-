package com.mobi.ecommerce.user;

import org.mapstruct.Mapper;

@Mapper(componentModel ="spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
