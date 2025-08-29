package com.letter_loom.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GameRound {
    private Set<String> submittedWords;
    private String gameWinner;
}
