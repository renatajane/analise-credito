import Styles from './Cpf.module.css';
import { Link, useLocation } from "react-router-dom";

const ClienteNaoCadastrado = () => {
    return (
        <div className={Styles.container}>
            <div className style={{ marginLeft: '25px', fontSize: '1.2rem' }}>Você precisa fazer o cadastro primeiro.</div>
            <div className={Styles['text-large']}style={{ marginLeft: '35px', fontSize: '1.2rem' }}>Clique no botão abaixo para fazer o</div>
            <div className={Styles['text-large']} style={{ marginLeft: '135px', fontSize: '1.2rem' }}>
                cadastro.
            </div>
            <Link
                className="menu-item"
                role="treeitem"
                to="/cadastroCliente"
            >
                <button className="br-button primary" style={{ marginLeft: '120px', marginTop: '12px' }}>
                    Cadastro
                </button>
            </Link>
        </div>
    );
};

export default ClienteNaoCadastrado;