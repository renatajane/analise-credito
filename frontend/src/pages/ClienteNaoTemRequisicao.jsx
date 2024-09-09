import Styles from './Cpf.module.css';
import { Link } from "react-router-dom";

const ClienteNaoTemRequisicao = () => {
    return (
        <div className={Styles.container}>
            <div className={Styles['text-large']} style={{ marginLeft: '20px', fontSize: '1.2rem' }}>
                Você ainda não tem nenhuma requisição
            </div>
            <div className={Styles['text-large']} style={{ marginLeft: '115px', fontSize: '1.2rem' }}>
                de empréstimo.
            </div>
            <div className={Styles['text-large']} style={{ marginLeft: '30px', fontSize: '1.2rem' }}>
                Clique no botão abaixo para fazer uma
            </div>
            <div className={Styles['text-large']} style={{ marginLeft: '115px', fontSize: '1.2rem' }}>
                requisição.
            </div>
            <div className={Styles.buttonContainer}>
                <Link
                    className="menu-item"
                    role="treeitem"
                    to="/cadastroRequisicao"
                >
                    <button className="br-button primary" style={{ marginLeft: '50px', marginTop: '12px' }}>
                        Requisição de Empréstimo
                    </button>
                </Link>
            </div>
        </div>
    );
};

export default ClienteNaoTemRequisicao;
