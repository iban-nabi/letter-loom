package com.letter_loom.controllers;


import com.letter_loom.dtos.response_dto.LeaderboardScoresResponse;
import com.letter_loom.dtos.response_dto.LeaderboardWinsResponse;
import com.letter_loom.services.LeaderboardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/get-leaderboards")
public class LeaderBoardsController {

    private final LeaderboardService leaderboardServices;

    @GetMapping("win")
    public List<LeaderboardWinsResponse> getLeaderBoards(){
        return leaderboardServices.getLeaderboardWinInfo();
    }


    @GetMapping("score")
    public List<LeaderboardScoresResponse> getLeaderboardScores(){
        return leaderboardServices.getLeaderboardScoreInfo();
    }
}
