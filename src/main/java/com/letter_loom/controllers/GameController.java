package com.letter_loom.controllers;

import com.letter_loom.dtos.request_dto.SubmittedAnswer;
import com.letter_loom.dtos.response_dto.SubmissionResponse;
import com.letter_loom.entities.User;
import com.letter_loom.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GameController {

    private final UserService userService;


    @GetMapping("/get-letters")
    public void getLetters(){}

    @MessageMapping("/submit-word")
    @SendTo("/game-client/playing")
    public SubmissionResponse sendWord(@Payload SubmittedAnswer submittedAnswer){
//        User user = userService.getUser();
        return SubmissionResponse.builder()
                .userSubmitted(submittedAnswer.getUsername())
                .score(100)
                .answer(submittedAnswer.getAnswer())
                .invalidAnswer(false)
                .build();
    }

    public void getSubmittedWords(){}
    public void showRoundWinner(){}
    public void showGameWinner(){}
    public void startGame(){}
    public void endGame(){}
    public void getTimer(){}
}
