import React from "react";
import trophy from "../images/trophy.svg";

interface GameData {
  playerAName: string;
  playerBName: string;
  winner: string;
  deuce: boolean;
  sequence: string;
  wonBallDtoList: {
    playerAName: string;
    playerBName: string;
    playerAScore: number;
    playerBScore: number;
  }[];
}

interface ScoreDisplayProps {
  gameData: GameData;
}

const ScoreDisplay: React.FC<ScoreDisplayProps> = ({ gameData }) => {
  const { playerAName, playerBName, wonBallDtoList, winner } = gameData;

  return (
    <div className="score-display">
      {wonBallDtoList.map((ball, index) => (
        <div key={index} className="score">
          <p>{`${playerAName} : ${ball.playerAScore} / ${playerBName} : ${ball.playerBScore}`}</p>
          {index === wonBallDtoList.length - 1 && (
            <div className="winner">
              {winner && (
                <div>
                  <p>{`${winner} wins the game`}</p>
                  <img src={trophy} alt="Trophy" className="trophy-icon" />
                </div>
              )}
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default ScoreDisplay;
