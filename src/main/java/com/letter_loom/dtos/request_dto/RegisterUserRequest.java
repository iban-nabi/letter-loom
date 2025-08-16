package com.letter_loom.dtos.request_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
