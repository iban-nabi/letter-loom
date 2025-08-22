package com.letter_loom.dtos.response_dto;

import com.letter_loom.entities.GameStatus;
import com.letter_loom.entities.GameWinner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class GameResponse {
    private Long id;
    private GameStatus status;
    private Date date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private GameWinner gameWinner;
    private int playerCount;

}
