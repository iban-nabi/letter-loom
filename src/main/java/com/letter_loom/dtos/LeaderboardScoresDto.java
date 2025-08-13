package com.letter_loom.dtos;


import com.letter_loom.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LeaderboardScoresDto {
    private Long userId;
    private String username;
    private Long score;
}
