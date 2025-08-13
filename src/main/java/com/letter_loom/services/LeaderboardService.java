package com.letter_loom.services;

import com.letter_loom.dtos.LeaderboardScoresDto;
import com.letter_loom.dtos.LeaderboardWinsDto;
import com.letter_loom.entities.GameWinner;
import com.letter_loom.entities.UserGame;
import com.letter_loom.repositories.GameWinnerRepository;
import com.letter_loom.repositories.UserGameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class LeaderboardService {

    private final GameWinnerRepository gameWinnerRepository;
    private final UserGameRepository userGameRepository;

    public List<LeaderboardWinsDto> getLeaderboardWinInfo(){
        List<GameWinner> gameWinners = gameWinnerRepository.findAll();
        Map<Long, LeaderboardWinsDto> leaderboardInfoDtoMap = new HashMap<>();
        LeaderboardWinsDto userLeaderboardInfoDto;

        for (GameWinner gameWinner : gameWinners) {
            Long id = gameWinner.getUser().getId();
            if(leaderboardInfoDtoMap.containsKey(id)){
                userLeaderboardInfoDto = leaderboardInfoDtoMap.get(id);
                userLeaderboardInfoDto.setNoOfWins(userLeaderboardInfoDto.getNoOfWins()+1);

            }else{
                userLeaderboardInfoDto = LeaderboardWinsDto.builder()
                        .userId(gameWinner.getUser().getId())
                        .username(gameWinner.getUser().getUsername())
                        .noOfWins(1L)
                        .build();
            }

            leaderboardInfoDtoMap.put(id, userLeaderboardInfoDto);
        }

        return populateLeaderBoardList(leaderboardInfoDtoMap);
    }

    private static List<LeaderboardWinsDto> populateLeaderBoardList(Map<Long, LeaderboardWinsDto>
                                                                                leaderboardInfoDtoMap){
        List<LeaderboardWinsDto> leaderboardInfoDtoList = new ArrayList<>();
        for(Map.Entry<Long, LeaderboardWinsDto> entry : leaderboardInfoDtoMap.entrySet()){
            leaderboardInfoDtoList.add(entry.getValue());
        }
        return leaderboardInfoDtoList;
    }

    public List<LeaderboardScoresDto> getLeaderboardScoreInfo() {
        return userGameRepository.getUserGamesGroupByScore();
    }
}
