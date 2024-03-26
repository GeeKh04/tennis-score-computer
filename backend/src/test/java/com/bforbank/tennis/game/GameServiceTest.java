package com.bforbank.tennis.game;

import com.bforbank.tennis.model.*;
import com.bforbank.tennis.repository.GameRepository;
import com.bforbank.tennis.service.GameMapper;
import com.bforbank.tennis.service.GameService;
import com.bforbank.tennis.service.GameServiceImpl;
import com.bforbank.tennis.shared.exception.GameException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameServiceTest {

    @InjectMocks
    private GameService gameService = new GameServiceImpl();
    @Mock
    private GameRepository gameRepository;
    private GameDto gameDto;
    @Mock
    private GameMapper gameMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gameDto = new GameDto();
        gameDto.setPlayerAName("A");
        gameDto.setPlayerBName("B");
    }
    @AfterEach
    public void clear() {
        Mockito.clearAllCaches();
    }

    @Test
    void createGame_invalidSequence() {
        GameDto gameDto = new GameDto("A", "B", "ABXX");
        assertThrows(GameException.class, () -> gameService.createGame(gameDto));
    }

    @Test
    void createGame_validSequence() {
        // Mocking
        GameDto gameDto = new GameDto("A", "B", "ABABABAB");
        Game savedGame = new Game(new Player("A", 0), new Player("B", 0), "ABABABAB");
        when(gameRepository.save(any())).thenReturn(savedGame);
        when(gameMapper.gameToGameDto(savedGame)).thenReturn(gameDto);

        // Test
        GameDto result = gameService.createGame(gameDto);

        // Verify
        assertNotNull(result);
        assertEquals(gameDto, result);
    }

    @Test
    void createGame_winA() {
        // Given
        String sequence = "ABABAA";
        gameDto.setSequence(sequence);

        Player playerA = new Player(gameDto.getPlayerAName(),0);
        Player playerB = new Player(gameDto.getPlayerBName(),0);
        Game game = new Game(playerA, playerB, sequence);
        List<WonBall> wonBallList = new ArrayList<>();
        wonBallList.add(new WonBall(playerA, 15, false));
        wonBallList.add(new WonBall(playerB, 15, false));
        wonBallList.add(new WonBall(playerA,  30, false));
        wonBallList.add(new WonBall(playerB, 30, false));
        wonBallList.add(new WonBall(playerA, 40, false));
        game.setWonBallList(wonBallList);
        game.setWinner("A");

        // Asserting the expected outcomes
        GameDto expected = new GameDto();
        expected.setSequence("ABABAA");
        expected.setPlayerAName("A");
        expected.setPlayerBName("B");
        expected.setWinner("A");
        expected.setDeuce(false);
        List<WonBallDto> wonBallDtoList = new ArrayList<>();
        wonBallDtoList.add(new WonBallDto("A", "B", 15, 0));
        wonBallDtoList.add(new WonBallDto("A", "B", 15, 15));
        wonBallDtoList.add(new WonBallDto("A", "B", 30, 15));
        wonBallDtoList.add(new WonBallDto("A", "B", 30, 30));
        wonBallDtoList.add(new WonBallDto("A", "B", 40, 30));
        expected.setWonBallDtoList(wonBallDtoList);

        // When
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(gameMapper.gameToGameDto(game)).thenReturn(expected);
        var result = gameService.createGame(gameDto);

        // Then
        verify(gameRepository).save(any(Game.class));
        verify(gameMapper).gameToGameDto(game);
        assertEquals(expected, result);
    }

    @Test
    void createGame_winB() {
        // Given
        String sequence = "ABABBB";
        GameDto inputGameDto = new GameDto();
        inputGameDto.setPlayerAName("A");
        inputGameDto.setPlayerBName("B");
        inputGameDto.setSequence(sequence);

        Player playerA = new Player(gameDto.getPlayerAName(),0);
        Player playerB = new Player(gameDto.getPlayerBName(),0);
        Game game = new Game(playerA, playerB, sequence);

        GameDto expectedGameDto = new GameDto();
        expectedGameDto.setPlayerAName("A");
        expectedGameDto.setPlayerBName("B");
        expectedGameDto.setWinner("A");

        // When
        when(gameRepository.save(any(Game.class))).thenReturn(new Game(playerA, playerB, sequence));
        when(gameMapper.gameToGameDto(game)).thenReturn(expectedGameDto);

        GameDto result = gameService.createGame(inputGameDto);

        // Then
        verify(gameRepository).save(any(Game.class));
        verify(gameMapper).gameToGameDto(game);
        assertEquals(expectedGameDto, result);
    }

    @Test
    public void testGameService_deuce_winA() {
        // Given
        gameDto.setSequence("BABABABABAA");

        Player playerA = new Player(gameDto.getPlayerAName(), 0);
        Player playerB = new Player(gameDto.getPlayerBName(), 0);
        Game game = new Game(playerA, playerB, gameDto.getSequence());
        game.setWinner("A");

        GameDto expectedGameDto = new GameDto();
        expectedGameDto.setPlayerAName("A");
        expectedGameDto.setPlayerBName("B");
        expectedGameDto.setSequence(game.getSequence());
        expectedGameDto.setDeuce(true);
        expectedGameDto.setWinner("A"); // Player B wins

        // When
        when(gameRepository.save(any(Game.class))).thenReturn(game); // Mock saving game
        when(gameMapper.gameToGameDto(any(Game.class))).thenReturn(expectedGameDto);
        var result = gameService.createGame(gameDto);

        // Asserting the expected outcomes
        GameDto expected = new GameDto();
        expected.setSequence("BABABABABAA");
        expected.setPlayerAName("A");
        expected.setPlayerBName("B");
        expected.setWinner("A");
        expected.setDeuce(true);

        assertEquals(expected, result);
    }

    @Test
    public void testGameService_deuce_winB() {
        // Given
        gameDto.setSequence("ABABABABABB");

        Player playerA = new Player(gameDto.getPlayerAName(), 0);
        Player playerB = new Player(gameDto.getPlayerBName(), 0);
        Game game = new Game(playerA, playerB, gameDto.getSequence());
        game.setWinner("B");

        GameDto expectedGameDto = new GameDto();
        expectedGameDto.setPlayerAName("A");
        expectedGameDto.setPlayerBName("B");
        expectedGameDto.setSequence(game.getSequence());
        expectedGameDto.setDeuce(true);
        expectedGameDto.setWinner("B"); // Player B wins

        // When
        when(gameRepository.save(any(Game.class))).thenReturn(game); // Mock saving game
        when(gameMapper.gameToGameDto(any(Game.class))).thenReturn(expectedGameDto);
        var result = gameService.createGame(gameDto);

        // Asserting the expected outcomes
        GameDto expected = new GameDto();
        expected.setSequence("ABABABABABB");
        expected.setPlayerAName("A");
        expected.setPlayerBName("B");
        expected.setWinner("B");
        expected.setDeuce(true);

        assertEquals(expected, result);
    }

    @Test
    void getGameById_existingGame() {
        // Given
        Long gameId = 1L;
        Game game = new Game(new Player("A", 0), new Player("B", 0), "ABABABAB");

        // When
        when(gameRepository.findById(gameId)).thenReturn(java.util.Optional.of(game));
        when(gameMapper.gameToGameDto(game)).thenReturn(new GameDto("A", "B", "ABABABAB"));

        // Then
        GameDto result = gameService.getGameById(gameId);

        // Verify
        assertNotNull(result);
        assertEquals("A", result.getPlayerAName());
        assertEquals("B", result.getPlayerBName());
        assertEquals("ABABABAB", result.getSequence());
    }

    @Test
    void getGameById_nonExistingGame() {
        // Given
        Long gameId = 1L;

        // When
        when(gameRepository.findById(gameId)).thenReturn(java.util.Optional.empty());

        // Then
        GameDto result = gameService.getGameById(gameId);

        // Verify
        assertNull(result);
    }
}
