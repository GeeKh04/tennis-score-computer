package com.bforbank.tennis.service;

import com.bforbank.tennis.model.GameDto;

public interface GameService {
    GameDto createGame(GameDto gameDto);
    GameDto getGameById(Long gameId);
}
