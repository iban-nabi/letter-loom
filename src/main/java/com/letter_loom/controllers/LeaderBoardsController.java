package com.letter_loom.controllers;


import com.letter_loom.dtos.UserLeaderboardInfoDto;
import com.letter_loom.services.LeaderboardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LeaderBoardsController {

    private final LeaderboardService leaderboardServices;

    @GetMapping("get-leaderboards")
    public List<UserLeaderboardInfoDto> getLeaderBoards(){
        return leaderboardServices.getLeaderboardInfo();
    }
}
