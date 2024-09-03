import Styles from './Cpf.module.css';
import { Link, useLocation } from "react-router-dom";

const ClienteNaoCadastrado = () => {
    return (
        <div className={Styles.container}>
            <div className={Styles['text-large']}>Você precisa fazer o cadastro primeiro.</div>
            <div className={Styles['text-large']}>Clique no botão abaixo para fazer o cadastro.</div>
            <Link
                className="menu-item"
                role="treeitem"
                to="/cadastroCliente"
            >
                <button className="br-button primary">
                    Cadastro
                </button>
            </Link>
        </div>
    );
};

export default ClienteNaoCadastrado;