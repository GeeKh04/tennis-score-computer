package com.bforbank.tennis.service;

import com.bforbank.tennis.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    Game gameDtoToGame(GameDto gameDto);

    @Mapping(target = "playerAName", source = "playerA.name")
    @Mapping(target = "playerBName", source = "playerB.name")
    @Mapping(target = "wonBallDtoList", expression = "java(mapWonBallList(game))")
    GameDto gameToGameDto(Game game);

    default List<WonBallDto> mapWonBallList(Game game) {
        List<WonBallDto> wonBallDtoList = new ArrayList<>();
        Player playerA = new Player(game.getPlayerA().getName(), 0);
        Player playerB = new Player(game.getPlayerB().getName(), 0);

        for (WonBall wonBall : game.getWonBallList()) {
            if (wonBall.getPlayer().getName().equals(playerA.getName())) {
                playerA.setScore(wonBall.getScore());
            } else if (wonBall.getPlayer().getName().equals(playerB.getName())) {
                playerB.setScore(wonBall.getScore());
            }
            var wBDTO = new WonBallDto(playerA.getName(), playerB.getName(), playerA.getScore(), playerB.getScore());
            wonBallDtoList.add(wBDTO);
        }
        return wonBallDtoList;
    }
}
