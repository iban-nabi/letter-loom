package com.letter_loom.mappers;

import com.letter_loom.dtos.RegisterUserRequest;
import com.letter_loom.dtos.UserDto;
import com.letter_loom.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User toEntity(RegisterUserRequest registerUserRequest);
}
