package com.bforbank.tennis.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Transient
    private int score;
    @Transient
    private boolean advantage;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
        this.advantage = false;
    }

    public Player(Player player) {
        this.name = player.name;
        this.score = player.score;
        this.advantage = player.advantage;
    }
}
