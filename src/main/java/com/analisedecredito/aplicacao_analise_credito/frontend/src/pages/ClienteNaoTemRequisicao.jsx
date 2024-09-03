import Styles from './Cpf.module.css';
import { Link } from "react-router-dom";

const ClienteNaoTemRequisicao = () => {
    return (
        <div className={Styles.container}>
            <div className={Styles['text-large'] }>
                Você ainda não tem nenhuma requisição de empréstimo.
            </div>
            <div className={Styles['text-large']}>
                Clique no botão abaixo para fazer uma requisição.
            </div>
            <div className={Styles.buttonContainer}>
                <Link
                    className="menu-item"
                    role="treeitem"
                    to="/cadastroRequisicao"
                >
                    <button className="br-button primary">
                        Requisição de Empréstimo
                    </button>
                </Link>
            </div>
        </div>
    );
};

export default ClienteNaoTemRequisicao;
