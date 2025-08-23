package com.letter_loom.repositories;

import com.letter_loom.entities.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalTime;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE g.status = 'ongoing' AND SIZE(g.games) < g.playerCount ORDER BY g.id ASC")
    List<Game> findOngoingGame(Pageable pageable);

    // Get All Available Games
    @Query("SELECT g FROM Game g WHERE g.status = 'waiting'")
    List<Game> findAllAvailableGames();

    // Update game
    @Modifying
    @Query("UPDATE Game g SET g.status = :status WHERE g.id = :id")
    void updateGameStatus(@Param("status") String status, @Param("id") Long id);

    @Modifying
    @Query("UPDATE Game g SET g.timeEnd = :time WHERE g.id = :id")
    void updateGameTimeEnd(@Param("time")LocalTime time, @Param("id") Long id);

    // remove game
}
