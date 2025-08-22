package com.letter_loom.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time_start", nullable = false)
    private LocalTime timeStart;

    @Column(name = "time_end", nullable = false)
    private LocalTime timeEnd;

    @OneToMany(mappedBy = "game")
    private List<UserGame> games = new ArrayList<>();

    @OneToOne(mappedBy = "game")
    private GameWinner gameWinner;

    @Column(name="player_count", nullable = false)
    private int playerCount;
}
