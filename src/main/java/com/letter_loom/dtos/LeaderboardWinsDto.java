package com.letter_loom.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LeaderboardWinsDto {
    private Long userId;
    private String username;
    private Long noOfWins;
}
