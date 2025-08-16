package com.letter_loom.dtos.request_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserPasswordRequest {
    private String oldPassword;
    private String newPassword;
}
