package com.example.fone_hub.mapper;


import com.example.fone_hub.dto.request.CreateUserRequest;
import com.example.fone_hub.dto.request.UpdateUserRequest;
import com.example.fone_hub.dto.response.UserResponse;
import com.example.fone_hub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User userEntity);

    User toUserEntity(CreateUserRequest newUser);

    void updateUserEntity(@MappingTarget User userEntity, UpdateUserRequest updateUserRequest);
}
