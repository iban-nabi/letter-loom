package com.letter_loom.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @GetMapping("/get-letters")
    public void getLetters(){}

    @PostMapping
    public void sendWord(){}
    public void getSubmittedWords(){}
    public void showRoundWinner(){}
    public void showGameWinner(){}
    public void startGame(){}
    public void endGame(){}
    public void getTimer(){}
}
