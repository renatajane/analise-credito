import React, { useState } from 'react';
import { cpfMask } from '../components/Mask/CpfMask';
import FormatDate from "../utils/formatDate";
import FormatValor from "../utils/formatCurrency";
import StylesTable from './Table.module.css';
import StylesCpf from './Cpf.module.css';
import { ApiService } from '../services/appService';
import { useAuth } from '../contexto/AuthProvider';

const StatusRequisicao = () => {
    const [cpf, setCpf] = useState('');
    const [requisicoes, setRequisicoes] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const { cpfLogado } = useAuth();

    const handleSearch = () => {
        const cpfNumerico = cpf.replace(/\D/g, '');
        if (cpfNumerico.length === 11) {
            setLoading(true);
            setError(null);

            const response = ApiService.Get(`emprestimo-requisicao/list-by-cpf?cpf=${cpfLogado}`)
            const data = Array.isArray(response.data) ? response.data : [];
            if (data.length > 0) {
                setRequisicoes(data);
            } else {
                setError('Nenhuma requisição encontrada para o CPF informado.');
            }
        } else {
            setError('CPF inválido. Verifique o formato e tente novamente.');
        }
    };

    const handleCpfChange = (e) => {
        setCpf(cpfMask(e.target.value));
        setError(null);
    };

    const handleBack = () => {
        setRequisicoes([]);
        setCpf('');
        setError(null);
    };

    const handleCloseError = () => {
        setError(null);
    };

    const renderStatusBadge = (status) => {
        const color = status === true ? 'green' : 'red';
        return (
            <span style={{
                display: 'inline-block',
                padding: '5px 10px',
                borderRadius: '15px',
                backgroundColor: color,
                color: 'white',
            }}>
                {status ? 'APROVADO' : 'NEGADO'}
            </span>
        );
    };

    return (
        <div>
            <div className={StylesTable.services}>
                <h3 className={StylesTable.color}>
                    Consultar Status de Operação de Crédito
                </h3>
            </div>

            <div className="d-flex justify-content-center align-items-center vh-100">
                <div className={`container text-center ${StylesCpf.container}`}>
                    {requisicoes.length === 0 ? (
                        <div className="col-sm-10 col-lg-7 mb-3 mx-auto mt-5">
                            <div className="br-input input-inline">
                                <div className="input-label">
                                    <label className="text-nowrap" htmlFor="cpf">CPF</label>
                                </div>
                                <div className="input-content">
                                    <input
                                        id="cpf"
                                        type="text"
                                        placeholder="Digite o CPF"
                                        value={cpf}
                                        onChange={handleCpfChange}
                                        maxLength={14}
                                        className="form-control"
                                    />
                                </div>
                            </div>
                            <button className="br-button secondary mb-4 mt-3" type="button" onClick={handleSearch}>
                                Buscar Requisições
                            </button>
                        </div>
                    ) : (
                        <div>
                            <div className={StylesTable.tableContainer}>
                                <table className={StylesTable.table}>
                                    <thead>
                                        <tr>
                                            <th scope="col" className={StylesTable.negritoBold}>Nome do Requerente</th>
                                            <th scope="col" className={StylesTable.negritoBold}>Valor Requerido</th>
                                            <th scope="col" className={StylesTable.negritoBold}>Data Requisição</th>
                                            <th scope="col" className={StylesTable.negritoBold}>Situação</th>
                                            <th scope="col" className={StylesTable.negritoBold}>Descrição do Situação</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {requisicoes.map((requisicao, index) => (
                                            <tr key={index}>
                                                <td>{requisicao.cliente.nome}</td>
                                                <td>{FormatValor(requisicao.valorRequerido)}</td>
                                                <td>{FormatDate(requisicao.dataRequisicao)}</td>
                                                <td>{renderStatusBadge(requisicao.aprovado)}</td>
                                                <td>{requisicao.descricaoResultado}</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>
                            <div className="d-flex justify-content-center mt-5">
                                <button className="br-button secondary" type="button" onClick={handleBack}>
                                    Buscar Outro CPF
                                </button>
                            </div>
                        </div>
                    )}

                    {loading && <p className="mt-4">Carregando...</p>}

                    {error && (
                        <div className="br-message danger mt-4">
                            <div className="icon">
                                <i className="fas fa-times-circle fa-lg" aria-hidden="true"></i>
                            </div>
                            <div className="content" role="alert">
                                <span className="message-title">Erro:</span>
                                <span className="message-body"> {error}</span>
                            </div>
                            <div className="close">
                                <button className="br-button circle small" type="button" aria-label="Fechar a mensagem de alerta" onClick={handleCloseError}>
                                    <i className="fas fa-times" aria-hidden="true"></i>
                                </button>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default StatusRequisicao;
