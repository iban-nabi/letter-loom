package com.letter_loom.repositories;

import com.letter_loom.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    // create game
    // get game
    // update game
    // remove game
}
