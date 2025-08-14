package com.letter_loom.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RegisterUserRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
