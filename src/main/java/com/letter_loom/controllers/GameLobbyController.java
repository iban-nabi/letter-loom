package com.letter_loom.controllers;

import com.letter_loom.dtos.request_dto.GameRequest;
import com.letter_loom.entities.Game;
import com.letter_loom.entities.GameStatus;
import com.letter_loom.entities.User;
import com.letter_loom.entities.UserGame;
import com.letter_loom.repositories.GameRepository;
import com.letter_loom.services.GameLobbyService;
import com.letter_loom.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class GameLobbyController {
    private final GameRepository gameRepository;
    private final GameLobbyService gameLobbyService;
    private final UserService userService;

    @Transactional
    @PostMapping("/join-game")
    public void joinRandomGame(){
        User user = userService.getUser();
        // get a game where status is ongoing and the size of user games is less than the number of size
        Game game = gameRepository.findOngoingGame(PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .orElse(null);

        // if game is null, create a new game
        if(game == null){
            game = gameLobbyService.createGame(5);
        }

        // create a new user game
        UserGame ug = gameLobbyService.createUserGame(user,game);
        game.getUserGames().add(ug);

        if(game.getUserGames().size()==game.getPlayerCount()){
            gameLobbyService.startGame(game);
        }
    }

    @PostMapping("/create-game")
    public ResponseEntity<?> createGame(@RequestBody GameRequest gameRequest){
        User user = userService.getUser();
        Game game = gameLobbyService.createGame(gameRequest.getPlayerCount());
        gameLobbyService.createUserGame(user,game);
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
