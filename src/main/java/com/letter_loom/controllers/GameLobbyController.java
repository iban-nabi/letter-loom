package com.letter_loom.controllers;

import com.letter_loom.entities.Game;
import com.letter_loom.repositories.GameRepository;
import com.letter_loom.services.GameLobbyService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameLobbyController {
    private final GameRepository gameRepository;
    private final GameLobbyService gameLobbyService;

    public GameLobbyController(GameRepository gameRepository, GameLobbyService gameLobbyService) {
        this.gameRepository = gameRepository;
        this.gameLobbyService = gameLobbyService;
    }

    public void joinRandomGame(Long userId){
        // get the list of games, if there is a game available, join there
        //else create a new game
    }

    public void createGame(Game game){
        gameRepository.save(game);
    }

    public List<Game> getGames(){
        return gameRepository.findAllAvailableGames();
    }

    public void joinGame(Long gameId){

    }
}
