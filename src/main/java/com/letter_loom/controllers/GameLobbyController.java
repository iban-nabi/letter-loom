package com.letter_loom.controllers;

import com.letter_loom.entities.Game;
import com.letter_loom.repositories.GameRepository;
import com.letter_loom.services.GameLobbyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    }

    public void createGame(){

    }

    public List<Game> getGames(){
        return gameRepository.findAllAvailableGames();
    }

    public void joinGame(Long gameId){

    }
}
