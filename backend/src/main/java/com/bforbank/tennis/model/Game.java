package com.bforbank.tennis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "playerA_id")
    private Player playerA;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "playerB_id")
    private Player playerB;
    @Column
    private String winner;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<WonBall> wonBallList;
    @Column
    private String sequence;
    @Column
    private Boolean deuce;

    public Game(Player playerA, Player playerB, String sequence) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.deuce = false;
        this.wonBallList = new ArrayList<>();
        this.sequence = sequence;
    }

}
