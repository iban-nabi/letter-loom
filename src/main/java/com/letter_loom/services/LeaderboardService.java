package com.letter_loom.services;

import com.letter_loom.dtos.UserLeaderboardInfoDto;
import com.letter_loom.entities.GameWinner;
import com.letter_loom.repositories.GameWinnerRepository;
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

    public List<UserLeaderboardInfoDto> getLeaderboardInfo(){
        List<GameWinner> gameWinners = gameWinnerRepository.findAll();
        Map<Long, UserLeaderboardInfoDto> leaderboardInfoDtoMap = new HashMap<>();
        UserLeaderboardInfoDto userLeaderboardInfoDto;

        for (GameWinner gameWinner : gameWinners) {
            Long id = gameWinner.getUser().getId();
            if(leaderboardInfoDtoMap.containsKey(id)){
                userLeaderboardInfoDto = leaderboardInfoDtoMap.get(id);
                userLeaderboardInfoDto.setNoOfWins(userLeaderboardInfoDto.getNoOfWins()+1);

            }else{
                userLeaderboardInfoDto = UserLeaderboardInfoDto.builder()
                        .userId(gameWinner.getUser().getId())
                        .username(gameWinner.getUser().getUsername())
                        .noOfWins(1L)
                        .build();
            }

            leaderboardInfoDtoMap.put(id, userLeaderboardInfoDto);
        }

        return populateLeaderBoardList(leaderboardInfoDtoMap);
    }

    private static List<UserLeaderboardInfoDto> populateLeaderBoardList(Map<Long, UserLeaderboardInfoDto>
                                                                                leaderboardInfoDtoMap){
        List<UserLeaderboardInfoDto> leaderboardInfoDtoList = new ArrayList<>();
        for(Map.Entry<Long,UserLeaderboardInfoDto> entry : leaderboardInfoDtoMap.entrySet()){
            leaderboardInfoDtoList.add(entry.getValue());
        }
        return leaderboardInfoDtoList;
    }
}
