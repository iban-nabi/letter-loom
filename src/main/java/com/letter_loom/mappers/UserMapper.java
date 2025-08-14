package com.letter_loom.mappers;

import com.letter_loom.dtos.UserDto;
import com.letter_loom.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
