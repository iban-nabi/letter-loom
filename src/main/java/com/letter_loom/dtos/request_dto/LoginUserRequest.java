package com.letter_loom.dtos.request_dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginUserRequest {
    @NotNull(message = "Empty Username")
    private String username;

    @NotNull(message = "Empty Password")
    private String password;
}
