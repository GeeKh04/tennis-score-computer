package com.bforbank.tennis.model;

import com.bforbank.tennis.model.Game;
import com.bforbank.tennis.model.Player;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class WonBall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int score;
    @Column
    private boolean advantage;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    @ManyToOne
    private Player player;

    public WonBall(Player player, int score, boolean advantage) {
        this.score = score;
        this.advantage = advantage;
        this.player = player;
    }
}
