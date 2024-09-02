import React, { useState, useEffect } from 'react';
import { cpfMask } from '../components/Mask/CpfMask';
import FormatDate from "../utils/formatDate";
import FormatValor from "../utils/formatCurrency";
import StylesTable from './Table.module.css';
import { ApiService } from '../services/appService';
import { useAuth } from '../contexto/AuthProvider';
import ClienteNaoCadastrado from '../pages/ClienteNaoCadastrado';

const StatusRequisicao = () => {
    const [cpf, setCpf] = useState('');
    const [requisicoes, setRequisicoes] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [idCliente, setIdCliente] = useState(null);

    const { cpfLogado } = useAuth();

    useEffect(() => {
        // Fetch ID do Cliente
        const fetchClientId = async () => {
            try {
                const response = await ApiService.Get(`cliente/cpf/${cpfLogado}`);
                if (response && response.data) {
                    setIdCliente(response.data.idCliente);
                    handleSearch();
                }
            } catch (error) {
                console.error('Erro ao buscar ID do cliente:', error);
            }
        };

        fetchClientId();
    }, []);

    const handleSearch = () => {
        const cpfNumerico = cpf.replace(/\D/g, '');
        if (cpfNumerico.length === 11) {
            setLoading(true);
            setError(null);

            const response = ApiService.Get(`emprestimo-requisicao/list-by-cpf?cpf=${cpfLogado}`)
            if (response) {
                const data = Array.isArray(response.data) ? response.data : [];
                if (data.length > 0) {
                    setRequisicoes(data);
                } else {
                    setError('Nenhuma requisição encontrada para o CPF informado.');
                }
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
            {idCliente == null && <ClienteNaoCadastrado />}
            {idCliente != null && requisicoes.length == 0 && <div>Você não tem requisições. Fazer requisicao. </div>} 
            {requisicoes.length > 0 && (
                <>
                    <div className={StylesTable.services}>
                        <h3 className={StylesTable.color}>
                            Consultar Status de Operação de Crédito
                        </h3>
                    </div>
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
                </>
            )}
        </div>
    );
};

export default StatusRequisicao;
