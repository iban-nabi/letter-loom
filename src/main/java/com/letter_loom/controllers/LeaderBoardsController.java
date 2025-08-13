package com.letter_loom.controllers;


import com.letter_loom.dtos.LeaderboardScoresDto;
import com.letter_loom.dtos.LeaderboardWinsDto;
import com.letter_loom.services.LeaderboardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LeaderBoardsController {

    private final LeaderboardService leaderboardServices;

    @GetMapping("get-leaderboards-win")
    public List<LeaderboardWinsDto> getLeaderBoards(){
        return leaderboardServices.getLeaderboardWinInfo();
    }


    @GetMapping("get-leaderboards-score")
    public List<LeaderboardScoresDto> getLeaderboardScores(){
        return leaderboardServices.getLeaderboardScoreInfo();
    }
}
