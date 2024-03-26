package com.bforbank.tennis.game;

import com.bforbank.tennis.model.Game;
import com.bforbank.tennis.model.Player;
import com.bforbank.tennis.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    void testSaveGame() {
        // Create a new game
        Game game = new Game(new Player("A", 40), new Player("B", 30), "ABABAA");
        game.setWinner("A");

        // Save the game to the repository
        Game savedGame = gameRepository.save(game);

        // Verify that the game is saved with an ID
        assertNotNull(savedGame.getId());

        // Retrieve the saved game from the repository
        Game retrievedGame = gameRepository.findById(savedGame.getId()).orElse(null);

        // Verify that the retrieved game is not null and has the correct properties
        assertNotNull(retrievedGame);
        assertEquals("A", retrievedGame.getPlayerA().getName());
        assertEquals("B", retrievedGame.getPlayerB().getName());
        assertEquals("A", retrievedGame.getWinner());
    }
}
