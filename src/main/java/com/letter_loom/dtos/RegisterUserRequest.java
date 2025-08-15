package com.letter_loom.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapping;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
