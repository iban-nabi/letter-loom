package com.letter_loom.repositories;

import com.letter_loom.entities.Game;
import com.letter_loom.entities.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserGameRepository extends JpaRepository<UserGame, Long> {
    // join game

    // update user game
    @Modifying
    @Query("UPDATE UserGame ug set ug.result = :result WHERE ug.id = :id " +
            "AND ug.game.id = :gameId AND ug.user.id = :userId")
    void updateGameResult(@Param("id") Long id, @Param("result") String result, @Param("gameId")
        Long gameId, @Param("userId") Long userId);

    // get game history of users
    @Query("SELECT g FROM Game g INNER JOIN UserGame ug ON ug.game WHERE ug.user.id = :id ")
    List<Game> findUserGameHistory(@Param("id") Long id);
}
