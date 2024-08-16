import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Header from "./componentes/Header/Header"
import HomeHeader from "./componentes/Header/HomeHeader";

function App() {
  return (
    <div className="App">
      <Routes>
        {/* Adicione outras rotas conforme necessário */}
      </Routes>
      <HomeHeader/>

    </div>
  );
}

export default App;
