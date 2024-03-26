package com.bforbank.tennis.service;

import com.bforbank.tennis.model.Game;
import com.bforbank.tennis.model.GameDto;
import com.bforbank.tennis.model.Player;
import com.bforbank.tennis.model.WonBall;
import com.bforbank.tennis.repository.GameRepository;
import com.bforbank.tennis.shared.exception.GameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;


/** Print the final score if the game hasn't ended **/
@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameMapper gameMapper;
    private Game game;

    public GameDto createGame(GameDto gameDto) throws GameException {
        // Initialize a new game with both players starting at 0 points
        Player playerA = new Player(gameDto.getPlayerAName(),0);
        Player playerB = new Player(gameDto.getPlayerBName(),0);
        game = new Game(playerA, playerB, gameDto.getSequence());

        playGame();

        // Save the game entity to the database
        Game savedGame = gameRepository.save(game);

        // Map the saved game entity back to GameDto and return
        return gameMapper.gameToGameDto(savedGame);
    }

    public GameDto getGameById(Long gameId) {
        // Retrieve the game entity from the database by ID
        return gameRepository.findById(gameId)
                .map(gameMapper::gameToGameDto)
                .orElse(null); // Return null if game not found
    }

    private void playGame() throws GameException {
        String[] sequence = game.getSequence().split("");
        validateSequence(sequence);
        for (String outcome : sequence) {
            processOutcome(outcome);
            if(game.getWinner()!=null) { break;}
            addScore(outcome);
        }
    }

    private void validateSequence(String[] sequence) throws GameException {
        boolean isValid = Arrays.stream(sequence)
                .allMatch(s -> s.equals(game.getPlayerA().getName()) || s.equals(game.getPlayerB().getName()));

        if (!isValid) {
            throw new GameException("Invalid character found in sequence");
        }
    }

    private void processOutcome(String outcome) {
        if (game.getPlayerA().getName().equals(outcome)) {
            handlePlayerOutcome(game.getPlayerA(), game.getPlayerB());
        } else if (game.getPlayerB().getName().equals(outcome)) {
            handlePlayerOutcome(game.getPlayerB(), game.getPlayerA());
        }
    }

    private void handlePlayerOutcome(Player player, Player opponent) {
        if (game.getDeuce()) {
            handleAdvantage(player, opponent);
        } else {
            handleNonDeuce(player, opponent);
        }
    }

    private void handleAdvantage(Player advantagedPlayer, Player opponent) {
        // If one player has advantage, they win the game
        if (advantagedPlayer.isAdvantage()) {
            game.setWinner(advantagedPlayer.getName());
        }
        // Otherwise, set advantage for player
        else {
            setAdvantage(advantagedPlayer, opponent);
        }
    }

    private void handleNonDeuce(Player player, Player opponent) {
        if (player.getScore() == 40 && opponent.getScore() != 40) {
            game.setWinner(player.getName());
        } else if (player.getScore() == 40) {
            game.setDeuce(true);
        } else {
            increaseScore(player);
        }
    }

    private void setAdvantage(Player advantagedPlayer, Player opponent) {
        advantagedPlayer.setAdvantage(true);
        opponent.setAdvantage(false);
    }

    private void increaseScore(Player player) {
        int newPoint = player.getScore() == 30 ? 10 : 15;
        player.setScore(player.getScore() + newPoint);
    }

    // All the current score to the WonBallList
    private void addScore(String outcome) {
        Player wonBallPlayer = null;
        if (game.getPlayerA().getName().equals(outcome)) {
            wonBallPlayer = game.getPlayerA();
        } else if (game.getPlayerB().getName().equals(outcome)) {
            wonBallPlayer = game.getPlayerB();
        }
        if (wonBallPlayer != null) {
            game.getWonBallList().add(new WonBall(wonBallPlayer, wonBallPlayer.getScore(),wonBallPlayer.isAdvantage()));
        }
    }
}
