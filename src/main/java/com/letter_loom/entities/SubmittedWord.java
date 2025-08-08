package com.letter_loom.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "submitted_words")
public class SubmittedWord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_game_id")
    private int userGameId;

    @Column(name = "word")
    private String word;
}
