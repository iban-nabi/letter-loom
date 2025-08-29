package com.letter_loom.dtos.response_dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionResponse {
    private String userSubmitted;
    private String answer;
    private int score;
    private boolean validAnswer;
}
