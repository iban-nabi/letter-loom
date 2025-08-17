package com.letter_loom.dtos.request_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserRequest {

    @NotNull(message = "Username should not be blank")
    @Size(max = 255, message = "Username should only be below 255 characters")
    private String username;

    @NotNull(message = "First name should not be blank")
    @Size(max = 255, message = "First name should only be below 255 characters")
    private String firstName;

    @NotNull(message = "Last name should not be blank")
    @Size(max = 255, message = "Last name should only be below 255 characters")
    private String lastName;

    @NotNull(message = "Email should not be blank")
    @Email(message = "Include a valid email")
    private String email;

    @NotNull(message = "Message should not be blank")
    @Size(min = 6, max = 25, message = "Password should should be at least 6-25 characters")
    private String password;
}
