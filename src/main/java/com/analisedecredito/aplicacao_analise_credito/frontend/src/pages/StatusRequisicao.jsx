import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import FormatDate from "../utils/formatDate";
import FormatValor from "../utils/formatCurrency";
import StylesTable from './Table.module.css';
import { ApiService } from '../services/appService';
import { useAuth } from '../contexto/AuthProvider';
import ClienteNaoCadastrado from '../pages/ClienteNaoCadastrado';
import ClienteNaoTemRequisicao from '../pages/ClienteNaoTemRequisicao';

const StatusRequisicao = () => {
    const [cpf, setCpf] = useState('');
    const [requisicoes, setRequisicoes] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [idCliente, setIdCliente] = useState(null);
    const navigate = useNavigate();

    const { cpfLogado } = useAuth();

    // Função para buscar requisições
    const fetchRequisicoes = async () => {
        try {
            setLoading(true);
            setError(null);

            // Buscar ID do Cliente
            const clienteResponse = await ApiService.Get(`cliente/cpf/${cpfLogado}`);
            if (clienteResponse && clienteResponse.data) {
                setIdCliente(clienteResponse.data.idCliente);

                // Buscar requisições usando o CPF
                const requisicoesResponse = await ApiService.Get(`emprestimo-requisicao/list-by-cpf?cpf=${cpfLogado}`);
                if (requisicoesResponse && requisicoesResponse.data) {
                    const data = Array.isArray(requisicoesResponse.data) ? requisicoesResponse.data : [];
                    if (data.length > 0) {
                        setRequisicoes(data);
                    } else {
                        setError('Nenhuma requisição encontrada para o CPF informado.');
                    }
                }
            } else {
                setError('Cliente não encontrado.');
            }
        } catch (err) {
            setError('Erro ao buscar requisições. Tente novamente.');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (cpfLogado) {
            fetchRequisicoes();
        }
    }, [cpfLogado]);

    const handleCloseError = () => {
        setError(null);
    };

    const renderStatusBadge = (status) => {
        const color = status ? 'green' : 'red';
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
            {idCliente != null && requisicoes.length === 0 && <ClienteNaoTemRequisicao />}
            {requisicoes.length > 0 && (
                <>
                    <div className={StylesTable.services}>
                        <h3 className={StylesTable.color}>
                            Consultar Status de Operação de Crédito
                        </h3>
                    </div>
                    <div>
                    <div className={StylesTable.container}>
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
                        
                        <div className="d-flex justify-content-center mt-5">
                            <button className="br-button secondary" type="button" onClick={() => navigate("/")}>
                                Voltar
                            </button>
                        </div>
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
