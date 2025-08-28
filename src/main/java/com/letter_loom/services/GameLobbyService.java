package com.letter_loom.services;

import com.letter_loom.entities.Game;
import com.letter_loom.entities.GameStatus;
import com.letter_loom.entities.User;
import com.letter_loom.entities.UserGame;
import com.letter_loom.objects.GameState;
import com.letter_loom.objects.Player;
import com.letter_loom.repositories.GameRepository;
import com.letter_loom.repositories.UserGameRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@AllArgsConstructor
public class GameLobbyService {
    //Repositories
    private final UserGameRepository userGameRepository;
    private final GameRepository gameRepository;

    //Services
    GameStateService gameStateService;

    @Transactional
    public Game createGame(int playerSize){
        Game game = Game.builder()
                .status(GameStatus.ongoing)
                .date(LocalDate.now())
                .timeStart(LocalTime.now())
                .playerCount(playerSize)
                .build();
        gameRepository.save(game);
        gameStateService.createGameState(game);
        return game;
    }

    @Transactional
    public UserGame createUserGame(User user, Game game){
        UserGame userGame = UserGame.builder()
                .user(user)
                .game(game)
                .build();
        userGameRepository.save(userGame);
        gameStateService.joinGameState(game,user);
        return userGame;
    }

    public void startGame(Game game) {
        game.setStatus(GameStatus.ongoing);
        gameRepository.save(game);
    }

}
