package com.letter_loom.repositories;

import com.letter_loom.dtos.LeaderboardScoresDto;
import com.letter_loom.entities.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserGameRepository extends JpaRepository<UserGame, Long> {
    // join game

    // get game leaderboards computing score
    @Query("SELECT ug.user.id, ug.user.username, SUM(ug.score) FROM UserGame ug GROUP BY ug.user")
    List<LeaderboardScoresDto> getUserGamesGroupByScore();

    // get game history of users

    // update user game
    @Modifying
    @Query("UPDATE UserGame ug SET ug.result = :result WHERE ug.id = :id " +
            "AND ug.game.id = :gameId AND ug.user.id = :userId")
    void updateGameResult(@Param("id") Long id, @Param("result") String result, @Param("gameId")
        Long gameId, @Param("userId") Long userId);


}
