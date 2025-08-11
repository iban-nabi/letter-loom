package com.letter_loom.repositories;

import com.letter_loom.entities.GameWinner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameWinnerRepository extends JpaRepository<GameWinner, Long> {
}