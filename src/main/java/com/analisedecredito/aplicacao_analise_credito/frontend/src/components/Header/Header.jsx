import React, { useEffect, useState } from "react";
import govbr from "../../Img/govbr.svg";
import MenuButton from "../Botoes/ButtonMenu";
import styles from "./Header.module.css";
import { Link } from "react-router-dom";
import { useAuth } from "../../contexto/AuthProvider";

function Header() {

  const { nomeLogado } = useAuth();

  const [saudacao, setSaudacao] = useState("Entrar");

  useEffect(() => {
    if (nomeLogado) {
      setSaudacao(`Olá, ${nomeLogado}`);
    } else {
      setSaudacao("Entrar")
    }
  }, [nomeLogado]);

  return (
    <header className="br-header">
      <div className="container-lg">
        <div className="header-top">
          <div className="header-logo">
            <div
              style={{ cursor: 'pointer' }}
              className="header-logo"
              onClick={() => (window.location.href = "https://www.gov.br/pt-br")}>
              <img src={govbr} alt="Logo do GovBR" />
            </div>
          </div>
          <div className="header-actions">
            <div className="header-links dropdown">
              <button
                className="br-button circle small"
                type="button"
                data-toggle="dropdown"
                aria-label="Abrir Acesso Rápido"
              >
                <i className="fas fa-ellipsis-v" aria-hidden="true"></i>
              </button>
              <div className="br-list">
                <div className="header">
                  <div className="title">Acesso Rápido</div>
                </div>
                <a
                  className="br-item"
                  href="https://www.gov.br/pt-br/orgaos-do-governo"
                >
                  Órgãos do Governo
                </a>
                <a
                  className="br-item"
                  href="https://www.gov.br/acessoainformacao/pt-br"
                >
                  Acesso à Informação
                </a>
                <a
                  className="br-item"
                  href="https://www4.planalto.gov.br/legislacao"
                >
                  Legislação
                </a>
                <a
                  className="br-item"
                  href="https://www.gov.br/governodigital/pt-br/acessibilidade-e-usuario/acessibilidade-digital"
                >
                  Acessibilidade
                </a>
              </div>
            </div>
          </div>
          <div className="header-login">
            <div className="header-sign-in">
              <Link to="/login">
                <button className="br-sign-in small" type="button" style={{ marginTop: '6px' }}
                  data-trigger="login"><i className="fas fa-user"
                    aria-hidden="true"></i><span className="d-sm-inline">{saudacao}</span>
                </button>
              </Link>
            </div>
            <div className="header-avatar"></div>
          </div>
        </div>
        <div className={styles.header_bottom}>
          <div className={styles.header_menu}>
            <div className={styles.header_menu_trigger}>
              <MenuButton />
            </div>
            <div className={styles.header_info}>
              <div className={styles.header_title}>Análise de Crédito</div>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;
