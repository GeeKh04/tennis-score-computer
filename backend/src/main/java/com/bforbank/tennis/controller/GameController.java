package com.bforbank.tennis.controller;

import com.bforbank.tennis.model.GameDto;
import com.bforbank.tennis.service.GameService;
import com.bforbank.tennis.shared.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// The REST API SERVICES
// Swagger link : http://localhost:8080/swagger-ui/index.html
@Tag(name = "Tennis Score Computer API", description = "API documentation for the Tennis Score Computer application")
@RestController
@RequestMapping("/api/v1/")
public class GameController {

    @Autowired
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Create a game and return the score data")
    @PostMapping("/createGame")
    public ResponseEntity<ApiResponse<GameDto>> createGame(@RequestBody GameDto gameDto) {
        try {
            GameDto createdGame = gameService.createGame(gameDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Game created successfully", createdGame));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to create the game", null));
        }
    }

    @Operation(summary = "Get a game by its id")
    @GetMapping("/{gameId}")
    public ResponseEntity<ApiResponse<GameDto>> getGameById(@PathVariable Long gameId) {
        GameDto gameDto = gameService.getGameById(gameId);
        if (gameDto != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Game found", gameDto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Game not found", null));
        }
    }

}
