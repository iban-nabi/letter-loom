package com.letter_loom.services;

import com.letter_loom.entities.Game;
import com.letter_loom.entities.User;
import com.letter_loom.objects.GameState;
import com.letter_loom.objects.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class GameStateService {
    private final Map<Long, GameState> activeGames = new ConcurrentHashMap<>();

    public void createGameState(Game game){
        GameState gameState = GameState.builder()
                .id(game.getId())
                .build();

        activeGames.put(gameState.getId(), gameState);
    }

    public void joinGameState(Game game, User user){
        Player player = Player.builder().username(user.getUsername()).build();
        GameState gameState = activeGames.get(game.getId());
        gameState.addPlayer(player);
    }

    public GameState getGameState(Long id){
        return activeGames.get(id);
    }
}
