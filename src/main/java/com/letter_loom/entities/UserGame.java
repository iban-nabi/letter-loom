package com.letter_loom.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_games")
public class UserGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "result", nullable = true)
    private String result;

    @Column(name = "winning_word", nullable = true)
    private String winningWord;

    @Column(name= "round_number")
    private Integer roundNumber;

    @Column(name = "score")
    private Long score;
}
