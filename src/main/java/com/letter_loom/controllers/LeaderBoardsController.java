package com.letter_loom.controllers;


import com.letter_loom.dtos.LeaderboardScoresDto;
import com.letter_loom.dtos.LeaderboardWinsDto;
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
    public List<LeaderboardWinsDto> getLeaderBoards(){
        return leaderboardServices.getLeaderboardWinInfo();
    }


    @GetMapping("score")
    public List<LeaderboardScoresDto> getLeaderboardScores(){
        return leaderboardServices.getLeaderboardScoreInfo();
    }
}
