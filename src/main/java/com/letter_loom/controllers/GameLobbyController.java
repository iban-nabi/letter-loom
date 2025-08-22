package com.letter_loom.controllers;

import com.letter_loom.dtos.request_dto.GameRequest;
import com.letter_loom.entities.Game;
import com.letter_loom.entities.GameStatus;
import com.letter_loom.entities.User;
import com.letter_loom.entities.UserGame;
import com.letter_loom.repositories.GameRepository;
import com.letter_loom.repositories.UserGameRepository;
import com.letter_loom.repositories.UserRepository;
import com.letter_loom.services.GameLobbyService;
import com.letter_loom.services.UserService;
import com.letter_loom.utilities.AuthenticationContextHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class GameLobbyController {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    private final AuthenticationContextHelper authHelper;
    private final GameLobbyService gameLobbyService;
    private final UserService userService;

    @PostMapping("/join-game")
    public void joinRandomGame(){

    }

    @PostMapping("/create-game")
    public ResponseEntity<?> createGame(@RequestBody GameRequest gameRequest){
        User user = userService.getUser();
        Game game = gameLobbyService.createGame(user, gameRequest.getPlayerCount());
        return ResponseEntity.ok(game);
    }

    @GetMapping("/get-available-games")
    public List<Game> getAvailableGames(){
        return gameRepository.findAllAvailableGames();
    }

    @PostMapping("/join-game/{id}")
    public ResponseEntity<?> joinGame(@PathVariable("id") Long gameId){
        User user = userService.getUser();
        Game game = gameRepository.findById(gameId).orElse(null);
        if(game==null || game.getStatus()!=GameStatus.waiting){
            return ResponseEntity.badRequest().build();
        }
        gameLobbyService.createUserGame(user,game);
        return ResponseEntity.ok(game);
    }
}
