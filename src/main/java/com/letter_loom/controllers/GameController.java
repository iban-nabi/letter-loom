package com.letter_loom.controllers;

import com.letter_loom.dtos.request_dto.SubmittedAnswer;
import com.letter_loom.dtos.response_dto.SubmissionResponse;
import com.letter_loom.services.GameService;
import com.letter_loom.services.GameStateService;
import com.letter_loom.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class GameController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;

    @GetMapping("/get-letters")
    public ResponseEntity<List<Character>> getLetters(){
        return ResponseEntity.ok(gameService.generateLetters());
    }

    @MessageMapping("/submit-word")
    public void sendWord(@Payload SubmittedAnswer submittedAnswer){
        String destination = "/game-room/" + submittedAnswer.getGameId();
        SubmissionResponse submissionResponse;

        boolean valid = gameService.verifyWord(submittedAnswer.getAnswer()) &&
                gameService.verifyNotDuplicate(submittedAnswer.getGameId(),
                        submittedAnswer.getAnswer());

        int score = gameService.generateScore(valid);

        submissionResponse = SubmissionResponse.builder()
                .userSubmitted(submittedAnswer.getUsername())
                .score(score)
                .answer(submittedAnswer.getAnswer())
                .validAnswer(valid)
                .build();

        messagingTemplate.convertAndSend(destination, submissionResponse);
    }

    public void getSubmittedWords(){}
    public void showRoundWinner(){}
    public void showGameWinner(){}
    public void startGame(){}
    public void endGame(){}
    public void getTimer(){}
}
