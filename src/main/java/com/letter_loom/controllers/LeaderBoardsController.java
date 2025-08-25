package com.letter_loom.controllers;


import com.letter_loom.dtos.response_dto.LeaderboardScoresResponse;
import com.letter_loom.dtos.response_dto.LeaderboardWinsResponse;
import com.letter_loom.services.LeaderboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/get-leaderboards")
public class LeaderBoardsController {

    private final LeaderboardService leaderboardServices;

    @GetMapping("/win")
    public ResponseEntity<List<LeaderboardWinsResponse>> getLeaderBoards(){
        return ResponseEntity.ok(leaderboardServices.getLeaderboardWinInfo());
    }


    @GetMapping("/score")
    public ResponseEntity<List<LeaderboardScoresResponse>> getLeaderboardScores(){
        return ResponseEntity.ok(leaderboardServices.getLeaderboardScoreInfo());
    }
}
