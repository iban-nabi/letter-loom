package com.letter_loom.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "user_games")
public class UserGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "game_id")
    private int gameId;
}
