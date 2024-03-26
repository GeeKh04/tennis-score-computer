package com.bforbank.tennis.game;

import com.bforbank.tennis.controller.GameController;
import com.bforbank.tennis.model.GameDto;
import com.bforbank.tennis.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class GameControllerTest {
    private MockMvc mockMvc;

    @Mock
    private GameService gameService;
    @InjectMocks
    private GameController gameController;
    @Mock
    Validator validator;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(gameController).setValidator(validator).build();
    }

    @Test
    void testCreateGame() throws Exception {
        GameDto createdGame = new GameDto("A", "B", "ABABAA");
        createdGame.setWinner("A");

        when(gameService.createGame(any(GameDto.class))).thenReturn(createdGame);

        mockMvc.perform(post("/api/v1/createGame")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerAName\":\"A\",\"playerBName\":\"B\",\"sequence\":\"ABABAA\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Game created successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.playerAName").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.playerBName").value("B"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.winner").value("A"));
    }

    @Test
    void testGetGameById() throws Exception {
        GameDto gameDto = new GameDto("A", "B", "ABABAA");
        Long gameId = 1L;

        when(gameService.getGameById(gameId)).thenReturn(gameDto);

        mockMvc.perform(get("/api/v1/{gameId}", gameId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Game found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.playerAName").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.playerBName").value("B"));
    }
}

