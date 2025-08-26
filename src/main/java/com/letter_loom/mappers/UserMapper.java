package com.letter_loom.mappers;

import com.letter_loom.dtos.request_dto.RegisterUserRequest;
import com.letter_loom.dtos.request_dto.UpdateUserRequest;
import com.letter_loom.dtos.response_dto.UserResponse;
import com.letter_loom.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDto(User user);
    User toEntity(RegisterUserRequest registerUserRequest);
    void updateEntity(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}
