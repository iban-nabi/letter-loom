package com.letter_loom.dtos.response_dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LeaderboardWinsResponse {
    private Long userId;
    private String username;
    private Long noOfWins;
}
