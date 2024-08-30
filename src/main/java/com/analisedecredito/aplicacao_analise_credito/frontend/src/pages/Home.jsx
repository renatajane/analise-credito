import React from "react";
import { Link } from "react-router-dom"; // Importa Link para navegação
import styles from "./Home.module.css";

const Home = () => {
    return (
        <div>
            <div className={styles.services}>
                <h1 className={styles.color}>
                    Seja bem-vindo ao Sistema de Análise de Crédito
                </h1>
            </div>

            <div className={styles.container}>
                <h1 className={styles.color}>Serviços</h1>
                <div>
                    <Link to="/cadastroCliente">
                        <h3>Cadastro</h3>
                    </Link>
                    <span className="br-divider my-3"></span>
                </div>
                <div>
                    <a href="http://192.168.37.8:3000/">
                        <h3>Simular Operações de Crédito</h3>
                    </a>
                    <span className="br-divider my-3"></span>
                </div>
                <div>
                    <Link to="/cadastroRequisicao">
                        <h3>Incluir Proposta de Contratação</h3>
                    </Link>
                    {/* <a href="http://192.168.37.8:5300/">
                        <h3>Incluir Proposta de Contratação</h3>
                        <span className="br-divider my-3"></span>
                    </a> */}
                </div>
                <div>
                    <Link to="/statusRequisicao">
                        <h3>Consultar Status de Operação de Crédito</h3>
                        <span className="br-divider my-3"></span>
                    </Link>
                </div>
                <div>
                    <Link to="/buscarRequisicao">
                        <h3>Gerar Relatórios</h3>
                        <span className="br-divider my-3"></span>
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Home;
