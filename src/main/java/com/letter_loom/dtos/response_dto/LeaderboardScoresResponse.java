package com.letter_loom.dtos.response_dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LeaderboardScoresResponse {
    private Long userId;
    private String username;
    private Long score;
}
