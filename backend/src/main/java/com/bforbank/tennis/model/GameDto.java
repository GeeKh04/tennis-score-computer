package com.bforbank.tennis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private Long id;
    private String playerAName;
    private String playerBName;
    private String winner;
    private Boolean deuce;
    private String sequence;
    private List<WonBallDto> wonBallDtoList;

    public GameDto(String playerAName, String playerBName, String sequence) {
        this.playerAName = playerAName;
        this.playerBName = playerBName;
        this.sequence = sequence;
    }

}
