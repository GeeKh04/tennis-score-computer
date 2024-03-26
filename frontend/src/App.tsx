import React from "react";
import GameBoard from "./components/GameBoard";
import logo from "./images/logo.svg";
import "./App.css";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Tennis Score</h1>
        {/*
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
  </a>*/}
        <GameBoard />
      </header>
    </div>
  );
}
/*
import React from "react";
import GameBoard from "./components/GameBoard";

const App: React.FC = () => {
  return (
    <div className="App">
      <h1>Tennis Score</h1>
      <GameBoard />
    </div>
  );
};*/

export default App;
