package com.letter_loom.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GameState {
    private Long id;
    private List<Player> players;
    private List<GameRound> rounds = new ArrayList<>();

    public void addWord(String word) {
        this.rounds.getLast().getSubmittedWords().add(word);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void createRound(){
        this.rounds.add(new GameRound());
    }
}
