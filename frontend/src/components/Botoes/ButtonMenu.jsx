import React, { useState, useEffect } from "react";
import styles from "./ButtonMenu.module.css";
import { Link, useLocation } from "react-router-dom";

const MenuButton = ({ isOpenProp }) => {
  const [isOpen, setIsOpen] = useState(isOpenProp || false);
  const [clienteOpen, setClienteOpen] = useState(false);
  const [operacoesOpen, setOperacoesOpen] = useState(false);
  const location = useLocation();

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  const toggleCliente = () => {
    setClienteOpen(!clienteOpen);
    setOperacoesOpen(false);
  };

  const toggleOperacoes = () => {
    setOperacoesOpen(!operacoesOpen);
    setClienteOpen(false);
  };

  useEffect(() => {
    setIsOpen(false);
    setClienteOpen(false);
    setOperacoesOpen(false);
  }, [location]);

  return (
    <div className={styles.menuContainer}>
      <div className={styles.menuButtonContainer}>
        <button
          className={`br-button small circle ${styles.menuButton}`}
          type="button"
          aria-label="Menu"
          data-toggle="menu"
          data-target="#main-navigation"
          onClick={toggleMenu}
        >
          <i className="fas fa-bars" aria-hidden="true"></i>
        </button>
      </div>
      <div
        className={`br-menu push active ${isOpen ? "" : "d-none"} ${styles.menu}`}
        id="main-navigation"
      >
        <div className="menu-panel">
          {isOpen && (
            <nav className="menu-body" role="tree">
              <div className="menu-folder">
                <Link
                  className="menu-item"
                  role="treeitem"
                  to="/"
                >
                  <span className="icon">
                    <i className="fas fa-home" aria-hidden="true"></i>
                  </span>
                  <span className="content">Home</span>
                </Link>
              </div>

              <div className="menu-folder">
                <a
                  className="menu-item"
                  role="treeitem"
                  onClick={toggleCliente}
                  style={{ cursor: "pointer" }}
                >
                  <span className="icon">
                    <i className="fas fa-user" aria-hidden="true"></i>
                  </span>
                  <span className="content">Cliente</span>
                </a>
                <ul className={`${clienteOpen ? "" : "d-none"}`}>
                  <li>
                    <Link
                      className="menu-item"
                      to="/cadastroCliente"
                      role="treeitem"
                    >
                      <span className="icon">
                        <i className="fas fa-id-card" aria-hidden="true"></i>
                      </span>
                      <span className="content">Cadastro</span>
                    </Link>
                  </li>
                </ul>
              </div>

              <div className="menu-folder">
                <a
                  className="menu-item"
                  role="treeitem"
                  onClick={toggleOperacoes}
                  style={{ cursor: "pointer" }}
                >
                  <span className="icon">
                    <i className="fas fa-dollar-sign" aria-hidden="true"></i> {/* Ícone de cifrão */}
                  </span>
                  <span className="content">Operações de Crédito</span>
                </a>
                <ul className={`${operacoesOpen ? "" : "d-none"}`}>
                  <li>
                  <Link
                      className="menu-item"
                      to="/cadastroRequisicao"
                      role="treeitem"
                    >                   
                      <span className="icon">
                        <i className="fas fa-hand-holding-usd" aria-hidden="true"></i>
                      </span>
                      <span className="content">Fazer Requisição de Empréstimo</span>
                      </Link>
                  </li>
                  <li>
                    <Link
                      className="menu-item"
                      to="/statusRequisicao"
                      role="treeitem"
                    >
                      <span className="icon">
                        <i className="fas fa-search" aria-hidden="true"></i>
                      </span>
                      <span className="content">Consultar Status de Empréstimo</span>
                    </Link>
                  </li>
                  <li>
                    <Link
                      className="menu-item"
                      to="/listarRequisicoes"
                      role="treeitem"
                    >
                      <span className="icon">
                        <i className="fas fa-chart-bar" aria-hidden="true"></i>
                      </span>
                      <span className="content">Gerar Relatórios</span>
                    </Link>
                  </li>
                </ul>
              </div>
            </nav>
          )}
        </div>
      </div>
      <div
        className={styles.menuScrim}
        data-dismiss="menu"
        tabIndex="0"
        onClick={toggleMenu}
      ></div>
    </div>
  );
};

export default MenuButton;
