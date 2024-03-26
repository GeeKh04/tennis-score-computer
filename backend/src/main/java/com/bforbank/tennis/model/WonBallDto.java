package com.bforbank.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WonBallDto {
    private String playerAName;
    private String playerBName;
    private int playerAScore;
    private int playerBScore;
}
