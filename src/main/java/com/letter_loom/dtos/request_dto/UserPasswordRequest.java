package com.letter_loom.dtos.request_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserPasswordRequest {
    @NotNull(message = "Old password is empty")
    private String oldPassword;

    @NotNull(message = "New password is empty")
    @Size(min = 6, max = 25, message = "New password should be 6-25 characters")
    private String newPassword;
}
