// GameBoard.tsx
import React, { useState } from "react";
import axios from "axios";
import ScoreDisplay from "./ScoreDisplay";
import "../styles/GameBoard.css"; // Import CSS file for styling

interface GameDto {
  playerAName: string;
  playerBName: string;
  sequence: string;
}

const GameBoard: React.FC = () => {
  const [sequence, setSequence] = useState("");
  const [gameData, setGameData] = useState<any>(null);
  const [error, setError] = useState<any>(null);

  const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api/v1/",
    headers: {
      "Content-Type": "application/json",
    },
  });

  const createGame = async (event: React.FormEvent) => {
    event.preventDefault();

    try {
      const gameDto: GameDto = {
        playerAName: "A", // Hardcoded player A name
        playerBName: "B", // Hardcoded player B name
        sequence: sequence,
      };

      const response = await axiosInstance.post("/createGame", gameDto);
      setGameData(response.data.data);
      setError(null);
    } catch (error: any) {
      console.error("Error fetching game data:", error);
      if (
        error.response &&
        error.response.data &&
        error.response.data.message
      ) {
        setError(error.response.data.message); // Assuming error response contains a message
      } else {
        setError("An error occurred while fetching game data.");
      }
    }
  };

  return (
    <div className="game-board">
      <form onSubmit={createGame}>
        <label>
          <input
            type="text"
            value={sequence}
            onChange={(e) => setSequence(e.target.value)}
          />
        </label>
        <button type="submit">Play</button>
      </form>
      {gameData && <ScoreDisplay gameData={gameData} />}
      {error && <p>Error: {error}</p>}
    </div>
  );
};

export default GameBoard;
