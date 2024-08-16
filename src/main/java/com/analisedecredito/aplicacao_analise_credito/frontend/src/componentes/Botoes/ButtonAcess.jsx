import React, { useState } from "react";
import styles from "../Botoes/ButtonAcess.module.css";

function ButtonAcess() {
  // Estado para controlar se o Dark Mode está ativado
  const [darkMode, setDarkMode] = useState(false);

  // Função para alternar entre o Dark Mode ativado/desativado
  const toggleDarkMode = () => {
    setDarkMode(!darkMode);
    // Lógica para alterar as cores do tema
    if (!darkMode) {
      document.documentElement.style.setProperty(
        "--background-color",
        "#212121"
      );
      document.documentElement.style.setProperty("--text-color", "#ffffff");
    } else {
      document.documentElement.style.setProperty(
        "--background-color",
        "#ffffff"
      );
      document.documentElement.style.setProperty("--text-color", "#212121");
    }
  };

  return (
    <div className="br-list">
      <div className="br-item">
        <button
          className={`br-button circle small ${styles.button_A}`}
          type="button"
          aria-label="Dark Mode"
          onClick={toggleDarkMode}
        >
          <i className="fas fa-adjust" aria-hidden="true"></i>
        </button>
      </div>
    </div>
  );
}

export default ButtonAcess;