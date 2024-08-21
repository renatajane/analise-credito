import React, { useState } from "react";
import styles from "./ButtonMenu.module.css";
import { Link } from "react-router-dom";

// const { REACT_APP_FRONT_URL } = import.meta.env;

const MenuButton = ({ isOpenProp }) => {
  const [isOpen, setIsOpen] = useState(isOpenProp || false);
  const [contribuintesOpen, setContribuintesOpen] = useState(false);
  const [dependentesOpen, setDependentesOpen] = useState(false);
  const [arvoreOpen, setArvoreOpen] = useState(false);

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  const toggleContribuintes = () => {
    setContribuintesOpen(!contribuintesOpen);
    setDependentesOpen(false);
    setArvoreOpen(false);
  };

  const toggleDependentes = () => {
    setDependentesOpen(!dependentesOpen);
    setContribuintesOpen(false);
    setArvoreOpen(false);
  };

  const toggleArvore = () => {
    setArvoreOpen(!arvoreOpen);
    setContribuintesOpen(false);
    setDependentesOpen(false);
  };

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
        className={`br-menu push active ${isOpen ? "" : "d-none"} ${styles.menu
          }`}
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
                <a
                  className="menu-item"
                  role="treeitem"
                  onClick={toggleContribuintes}
                  style={{ cursor: "pointer" }}
                >
                  <span className="icon">
                    <i className="fas fa-bell" aria-hidden="true"></i>
                  </span>
                  <span className="content">Contribuintes</span>
                </a>
                <ul className={`${contribuintesOpen ? "" : "d-none"}`}>
                  <li>
                    <a
                      className="menu-item"
                      href="http://192.168.37.8:8090/cadastroContribuintes"
                      role="treeitem"
                    >
                      <span className="icon">
                        <i className="fas fa-heart" aria-hidden="true"></i>
                      </span>
                      <span className="content">Cadastrar</span>
                    </a>
                  </li>
                  <li>
                    <a
                      className="menu-item"
                      href="http://192.168.37.8:8090/cadastro"
                      role="treeitem"
                    >
                      <span className="icon">
                        <i className="fas fa-heart" aria-hidden="true"></i>
                      </span>
                      <span className="content">Consultar Contribuinte</span>
                    </a>
                  </li>
                  <li>
                    <a
                      className="menu-item"
                      href="http://192.168.37.8:8090/atualizaCadastro"
                      role="treeitem"
                    >
                      <span className="icon">
                        <i className="fas fa-archive" aria-hidden="true"></i>
                      </span>
                      <span className="content">Atualizar Cadastro</span>
                    </a>
                  </li>
                  <li>
                    <a
                      className="menu-item"
                      href="http://192.168.37.8:8090/infoContribuicao"
                      role="treeitem"
                    >
                      <span className="icon">
                        <i
                          className="fas fa-address-book"
                          aria-hidden="true"
                        ></i>
                      </span>
                      <span className="content">
                        Informação de Contribuição
                      </span>
                    </a>
                  </li>
                </ul>
              </div>
              <div className="menu-folder">
                <a
                  className="menu-item"
                  role="treeitem"
                  onClick={toggleDependentes}
                  style={{ cursor: "pointer" }}
                >
                  <span className="icon">
                    <i className="fas fa-bell" aria-hidden="true"></i>
                  </span>
                  <span className="content">Dependentes</span>
                </a>
                <ul className={`${dependentesOpen ? "" : "d-none"}`}>
                  <li>
                    <a
                      className="menu-item"
                      href="http://192.168.37.8:8090/cadastroDependentes"
                      role="treeitem"
                    >
                      <span className="icon">
                        <i className="fas fa-heart" aria-hidden="true"></i>
                      </span>
                      <span className="content">Cadastrar</span>
                    </a>
                  </li>
                  <li>
                    <a
                      className="menu-item"
                      href="http://192.168.37.8:8090/atualizaDependentes"
                      role="treeitem"
                    >
                      <span className="icon">
                        <i className="fas fa-heart" aria-hidden="true"></i>
                      </span>
                      <span className="content">Excluir Dependentes</span>
                    </a>
                  </li>
                </ul>
              </div>
              <div className="menu-folder">
                <a
                  className="menu-item"
                  role="treeitem"
                  onClick={toggleArvore}
                  style={{ cursor: "pointer" }}
                >
                  <span className="icon">
                    <i className="fas fa-bell" aria-hidden="true"></i>
                  </span>
                  <span className="content">Arvore Genealógica</span>
                </a>
                <ul className={`${arvoreOpen ? "" : "d-none"}`}>
                  <li>
                    <a
                      className="menu-item"
                      href="http://192.168.37.8:8090/arvoregenealogica"
                      role="treeitem"
                    >
                      <span className="icon">
                        <i className="fas fa-heart" aria-hidden="true"></i>
                      </span>
                      <span className="content">Consultar Familiares</span>
                    </a>
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
