package com.letter_loom.dtos.request_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserRequest {
    @NotNull(message = "First name should not be empty")
    @Size(max = 255, message = "First Name should only be under 255 characters")
    private String firstName;

    @NotNull(message = "Last name should not be empty")
    @Size(max = 255, message = "Last Name should only be under 255 characters")
    private String lastName;
}
