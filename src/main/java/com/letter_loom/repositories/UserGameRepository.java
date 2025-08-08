package com.letter_loom.repositories;

import com.letter_loom.entities.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGameRepository extends JpaRepository<UserGame, Long> {
    // join game
    // update user game
}
