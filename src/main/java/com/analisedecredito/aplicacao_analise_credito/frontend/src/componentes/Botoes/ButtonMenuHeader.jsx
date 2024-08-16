import React, { useState } from "react";
import styles from "./ButtonMenu.module.css";


const MenuButtonHeader = ({ isOpenProp }) => {
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
        className={`br-menu push active ${isOpen ? "" : "d-none"} ${
          styles.menu
        }`}
        id="main-navigation"
      >
        <div className="menu-panel">
          {isOpen && (
            <nav className="menu-body" role="tree">
              <div className="menu-folder">
                <a
                  className="menu-item"
                  role="treeitem"
                  href="http://192.168.37.8:8090"
                >
                  <span className="icon">
                    <i className="fas fa-bell" aria-hidden="true"></i>
                  </span>
                  <span className="content">Home</span>
                </a>
                <a
                  className="menu-item"
                  role="treeitem"
                  href="http://192.168.37.8:8090/login"
                >
                  <span className="icon">
                    <i className="fas fa-bell" aria-hidden="true"></i>
                  </span>
                  <span className="content">Gestão de Contribuintes</span>
                </a>
                <a
                  className="menu-item"
                  role="treeitem"
                  href="http://192.168.37.8:3000/"
                >
                  <span className="icon">
                    <i className="fas fa-bell" aria-hidden="true"></i>
                  </span>
                  <span className="content">Gestão de Contribuição</span>
                </a>
                <a
                  className="menu-item"
                  role="treeitem"
                  href="http://192.168.37.8:5300/"
                >
                  <span className="icon">
                    <i className="fas fa-bell" aria-hidden="true"></i>
                  </span>
                  <span className="content">Gestão de Benefícios</span>
                </a>
                <a
                  className="menu-item"
                  role="treeitem"
                  href="http://192.168.37.8:8080/"
                >
                  <span className="icon">
                    <i className="fas fa-bell" aria-hidden="true"></i>
                  </span>
                  <span className="content">Gestão de Empréstimos</span>
                </a>
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

export default MenuButtonHeader;