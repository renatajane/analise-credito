import React, { useState, useEffect, useRef } from 'react';
import styles from './CadastroRequisicao.module.css';
import StylesTable from './Table.module.css';
import Styles from './Cpf.module.css';
import { useLocation, useNavigate } from 'react-router-dom';
import { ApiService } from '../services/appService';
import { useAuth } from '../contexto/AuthProvider';
import ClienteNaoCadastrado from './ClienteNaoCadastrado';

const CadastroRequisicao = () => {
    const [valorRequerido, setValorRequerido] = useState('');
    const [diaPagamento, setDiaPagamento] = useState('');
    const [prazoMes, setPrazoMes] = useState('');
    const navigate = useNavigate();
    const [modalidades, setModalidades] = useState([]);
    const [loading, setLoading] = useState(false);
    const [objetivos, setObjetivos] = useState([]);
    const [valorMaximoPreAprovado, setValorMaximoPreAprovado] = useState('');
    const [urgencias, setUrgencias] = useState([]);
    const [idCliente, setIdCliente] = useState(null); // Inicialize como null
    const [modalidadesPagamento, setModalidadesPagamento] = useState([]);

    const [selectedModalidade, setSelectedModalidade] = useState(null);
    const [selectedObjetivo, setSelectedObjetivo] = useState(null);
    const [selectedUrgencia, setSelectedUrgencia] = useState(null);
    const [selectedDiaPagamento, setSelectedDiaPagamento] = useState(null);
    const [selectedPrazoMes, setSelectedPrazoMes] = useState(null);
    const [selectedModalidadesPagamento, setSelectedModalidadesPagamento] = useState(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const [isModalidadeListVisible, setIsModalidadeListVisible] = useState(false);
    const [isObjetivoListVisible, setIsObjetivoListVisible] = useState(false);
    const [isUrgenciaListVisible, setIsUrgenciaListVisible] = useState(false);
    const [isDiaPagamentoListVisible, setIsDiaPagamentoListVisible] = useState(false);
    const [isPrazoMesListVisible, setIsPrazoMesListVisible] = useState(false);
    const [isModalidadePagamentoListVisible, setIsModalidadePagamentoListVisible] = useState(false);

    const modalidadeRef = useRef(null);
    const objetivoRef = useRef(null);
    const urgenciaRef = useRef(null);
    const diaPagamentoRef = useRef(null);
    const prazoMesRef = useRef(null);

    const { cpfLogado } = useAuth();

    const [error, setError] = useState(null);

    const [feedbackMessage, setFeedbackMessage] = useState('');

    // Opções fixas para o dia de pagamento
    const diasPagamento = [
        { valor: 5, descricao: '5' },
        { valor: 10, descricao: '10' },
        { valor: 15, descricao: '15' },
        { valor: 20, descricao: '20' },
        { valor: 25, descricao: '25' }
    ];

    // Opções fixas para o prazo de pagamento em meses
    const prazoMesPagamento = [
        { valor: 6, descricao: '6 meses' },
        { valor: 10, descricao: '10 meses' },
        { valor: 12, descricao: '12 meses' },
        { valor: 16, descricao: '16 meses' },
        { valor: 24, descricao: '24 meses' }
    ];

    useEffect(() => {
        // Fetch ID do Cliente
        const fetchClientId = async () => {
            try {
                setLoading(true);
                const response = await ApiService.Get(`cliente/cpf/${cpfLogado}`);
                if (response && response.data) {
                    setIdCliente(response.data.idCliente);
                    setValorMaximoPreAprovado(response.data.valorMaximoPreAprovado);
                }
            } catch (error) {
                console.error('Erro ao buscar ID do cliente:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchClientId();
    }, []);

    useEffect(() => {
        // Fetch dados adicionais após o ID do cliente ser definido
        if (idCliente) {
            const fetchData = async () => {
                try {
                    const [modalidadesRes, objetivosRes, urgenciasRes, modalidadesPagamentoRes] = await Promise.all([
                        ApiService.Get('emprestimo-modalidade/list'),
                        ApiService.Get('emprestimo-objetivo/list'),
                        ApiService.Get('emprestimo-urgencia/list'),
                        ApiService.Get('modalidade-pagamento/list'),
                    ]);
                    setModalidades(modalidadesRes.data);
                    setObjetivos(objetivosRes.data);
                    setUrgencias(urgenciasRes.data);
                    setModalidadesPagamento(modalidadesPagamentoRes.data);
                    console.log('meus dadoooss****', modalidadesPagamentoRes.data);
                } catch (error) {
                    console.error('Erro ao carregar os dados:', error);
                }
            };
            fetchData();
        }
    }, [idCliente]);

    const toggleListVisibility = (setter) => setter(prev => !prev);

    // Função para formatar números como moeda brasileira
    const formatCurrency = (value) => {
        if (value === null || value === undefined) return 'R$ 0,00';
        return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
    };

    const handleCloseError = () => {
        setError(null);
    };


    const handleOptionSelect = (option, setter, setVisibility) => {
        setter(option);
        setVisibility(false);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!idCliente) {
            setFeedbackMessage('Cliente não encontrado.');
            return;
        }


        const data = {
            cliente: idCliente, // Use o ID do cliente obtido
            emprestimoModalidade: selectedModalidade?.idModalidade,
            valorRequerido,
            emprestimoObjetivo: selectedObjetivo?.idObjetivo,
            emprestimoUrgencia: selectedUrgencia?.idUrgencia,
            modalidadePagamento: selectedModalidadesPagamento?.idModalidadePagamento,
            diaPagamento: selectedDiaPagamento?.valor, // Usando valor numérico selecionado
            prazoMes: selectedPrazoMes?.valor // Usando valor numérico selecionado
        };

        try {
            setIsSubmitting(true); // Desabilitar botão
            const response = await ApiService.Post('emprestimo-requisicao', data);
            setFeedbackMessage('Requisição enviada com sucesso!');
            setModalidades('');
            setValorRequerido('');
            setObjetivos('');
            setUrgencias('');
            setModalidadesPagamento('');
            setDiaPagamento('');
            setPrazoMes('');

        } catch (error) {
            console.error('Erro ao enviar a requisição:', error);
            setFeedbackMessage('Erro ao enviar a requisição. Tente novamente.');
        } finally {
            setIsSubmitting(false); // Reabilitar botão após a requisição
        }
    };

    return (
        <div>
            {loading ? (
                <div className={Styles.container} style={{ fontSize: '1.2rem' }}>
                    Carregando...
                </div>
            ) : (
                <>
                    {idCliente == null && <ClienteNaoCadastrado />}
                    {idCliente > 0 && (
                        <>
                            <div className={StylesTable.services}>
                                <h3 className={StylesTable.color} style={{ marginBottom: '25px' }}>
                                    Olá, seu valor pré-aprovado para empréstimo é de {formatCurrency(valorMaximoPreAprovado)}
                                </h3>
                            </div>
                            <div className={styles.container}>
                                <h5 className="requisicao" style={{ textTransform: 'none' }}>
                                    Incluir Proposta de Requisição
                                </h5>
                                <form onSubmit={handleSubmit}>
                                    <div className="row">
                                        <div className="col-sm-8 col-lg-5">
                                            <div className="br-input">
                                                <label htmlFor="valorRequerido">Valor Requerido <span className={styles.obrigatorio}>(Obrigatório)</span></label>
                                                <input
                                                    id="valorRequerido"
                                                    type="number"
                                                    placeholder="Digite o valor requerido"
                                                    value={valorRequerido}
                                                    onChange={(e) => setValorRequerido(e.target.value)}
                                                />
                                            </div>
                                        </div>

                                        {/* Modalidade de Empréstimo */}
                                        <div className="col-sm-20 col-lg-30 mb-2">
                                            <label className="text-nowrap" htmlFor="modalidade">
                                                Modalidade de Empréstimo <span className={styles.obrigatorio}>(Obrigatório)</span>
                                            </label>
                                            <div className={styles.brselect} ref={modalidadeRef}>
                                                <div className="br-input">
                                                    <input
                                                        id="modalidade"
                                                        type="text"
                                                        className="br-input"
                                                        placeholder="Selecione a modalidade"
                                                        value={selectedModalidade?.descricaoModalidade || ''}
                                                        onClick={() => toggleListVisibility(setIsModalidadeListVisible)}
                                                        readOnly
                                                    />
                                                    <button
                                                        className="br-button"
                                                        type="button"
                                                        aria-label="Exibir lista"
                                                        onClick={() => toggleListVisibility(setIsModalidadeListVisible)}
                                                    >
                                                        <i className="fas fa-angle-down" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                                {isModalidadeListVisible && (
                                                    <ul className={styles.dropdownList}>
                                                        {modalidades.map((modalidade) => (
                                                            <li
                                                                key={modalidade.idModalidade}
                                                                onClick={() => handleOptionSelect(modalidade, setSelectedModalidade, setIsModalidadeListVisible)}
                                                                className={styles.dropdownItem}
                                                            >
                                                                {modalidade.descricaoModalidade}
                                                            </li>
                                                        ))}
                                                    </ul>
                                                )}
                                            </div>
                                        </div>

                                        {/* Objetivo do Empréstimo */}
                                        <div className="col-sm-20 col-lg-30 mb-2">
                                            <label className="text-nowrap" htmlFor="objetivo">
                                                Objetivo do Empréstimo <span className={styles.obrigatorio}>(Obrigatório)</span>
                                            </label>
                                            <div className={styles.brselect} ref={objetivoRef}>
                                                <div className="br-input">
                                                    <input
                                                        id="objetivo"
                                                        type="text"
                                                        className="br-input"
                                                        placeholder="Selecione o objetivo"
                                                        value={selectedObjetivo?.descricaoObjetivo || ''}
                                                        onClick={() => toggleListVisibility(setIsObjetivoListVisible)}
                                                        readOnly
                                                    />
                                                    <button
                                                        className="br-button"
                                                        type="button"
                                                        aria-label="Exibir lista"
                                                        onClick={() => toggleListVisibility(setIsObjetivoListVisible)}
                                                    >
                                                        <i className="fas fa-angle-down" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                                {isObjetivoListVisible && (
                                                    <ul className={styles.dropdownList}>
                                                        {objetivos.map((objetivo) => (
                                                            <li
                                                                key={objetivo.idObjetivo}
                                                                onClick={() => handleOptionSelect(objetivo, setSelectedObjetivo, setIsObjetivoListVisible)}
                                                                className={styles.dropdownItem}
                                                            >
                                                                {objetivo.descricaoObjetivo}
                                                            </li>
                                                        ))}
                                                    </ul>
                                                )}
                                            </div>
                                        </div>

                                        {/* Urgência */}
                                        <div className="col-sm-20 col-lg-30 mb-2">
                                            <label className="text-nowrap" htmlFor="urgencia">
                                                Urgência <span className={styles.obrigatorio}>(Obrigatório)</span>
                                            </label>
                                            <div className={styles.brselect} ref={urgenciaRef}>
                                                <div className="br-input">
                                                    <input
                                                        id="urgencia"
                                                        type="text"
                                                        className="br-input"
                                                        placeholder="Selecione a urgência"
                                                        value={selectedUrgencia?.prazoUrgencia || ''}
                                                        onClick={() => toggleListVisibility(setIsUrgenciaListVisible)}
                                                        readOnly
                                                    />
                                                    <button
                                                        className="br-button"
                                                        type="button"
                                                        aria-label="Exibir lista"
                                                        onClick={() => toggleListVisibility(setIsUrgenciaListVisible)}
                                                    >
                                                        <i className="fas fa-angle-down" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                                {isUrgenciaListVisible && (
                                                    <ul className={styles.dropdownList}>
                                                        {urgencias.map((urgencia) => (
                                                            <li
                                                                key={urgencia.idUrgencia}
                                                                onClick={() => handleOptionSelect(urgencia, setSelectedUrgencia, setIsUrgenciaListVisible)}
                                                                className={styles.dropdownItem}
                                                            >
                                                                {urgencia.prazoUrgencia}
                                                            </li>
                                                        ))}
                                                    </ul>
                                                )}
                                            </div>
                                        </div>

                                        {/* Modalidade de Pagamento */}
                                        <div className="col-sm-20 col-lg-30 mb-2">
                                            <label className="text-nowrap" htmlFor="modalidadePagamento">
                                                Modalidade de Pagamento <span className={styles.obrigatorio}>(Obrigatório)</span>
                                            </label>
                                            <div className={styles.brselect} ref={diaPagamentoRef}>
                                                <div className="br-input">
                                                    <input
                                                        id="modalidadePagamento"
                                                        type="text"
                                                        className="br-input"
                                                        placeholder="Selecione a modalidade de pagamento"
                                                        value={selectedModalidadesPagamento?.descricaoPagamento || ''}
                                                        onClick={() => toggleListVisibility(setIsModalidadePagamentoListVisible)}
                                                        readOnly
                                                    />
                                                    <button
                                                        className="br-button"
                                                        type="button"
                                                        aria-label="Exibir lista"
                                                        onClick={() => toggleListVisibility(setIsModalidadePagamentoListVisible)}
                                                    >
                                                        <i className="fas fa-angle-down" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                                {isModalidadePagamentoListVisible && (
                                                    <ul className={styles.dropdownList}>
                                                        {modalidadesPagamento.map((modalidadePagamento) => (
                                                            <li
                                                                key={modalidadePagamento.idModalidadePagamento}
                                                                onClick={() => handleOptionSelect(modalidadePagamento, setSelectedModalidadesPagamento, setIsModalidadePagamentoListVisible)}
                                                                className={styles.dropdownItem}
                                                            >
                                                                {modalidadePagamento.descricaoPagamento}
                                                            </li>
                                                        ))}
                                                    </ul>
                                                )}
                                            </div>
                                        </div>

                                        {/* Dia de Pagamento */}
                                        <div className="col-sm-20 col-lg-30 mb-2">
                                            <label className="text-nowrap" htmlFor="diaPagamento">
                                                Dia de Pagamento <span className={styles.obrigatorio}>(Obrigatório)</span>
                                            </label>
                                            <div className={styles.brselect} ref={diaPagamentoRef}>
                                                <div className="br-input">
                                                    <input
                                                        id="diaPagamento"
                                                        type="text"
                                                        className="br-input"
                                                        placeholder="Selecione o dia de pagamento"
                                                        value={selectedDiaPagamento?.descricao || ''}
                                                        onClick={() => toggleListVisibility(setIsDiaPagamentoListVisible)}
                                                        readOnly
                                                    />
                                                    <button
                                                        className="br-button"
                                                        type="button"
                                                        aria-label="Exibir lista"
                                                        onClick={() => toggleListVisibility(setIsDiaPagamentoListVisible)}
                                                    >
                                                        <i className="fas fa-angle-down" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                                {isDiaPagamentoListVisible && (
                                                    <ul className={styles.dropdownList}>
                                                        {diasPagamento.map((dia) => (
                                                            <li
                                                                key={dia.valor}
                                                                onClick={() => handleOptionSelect(dia, setSelectedDiaPagamento, setIsDiaPagamentoListVisible)}
                                                                className={styles.dropdownItem}
                                                            >
                                                                {dia.descricao}
                                                            </li>
                                                        ))}
                                                    </ul>
                                                )}
                                            </div>
                                        </div>

                                        {/* Prazo em Meses */}
                                        <div className="col-sm-20 col-lg-30 mb-2">
                                            <label className="text-nowrap" htmlFor="prazoMes">
                                                Prazo em Meses para Pagamento <span className={styles.obrigatorio}>(Obrigatório)</span>
                                            </label>
                                            <div className={styles.brselect} ref={prazoMesRef}>
                                                <div className="br-input">
                                                    <input
                                                        id="prazoMes"
                                                        type="text"
                                                        className="br-input"
                                                        placeholder="Selecione o prazo"
                                                        value={selectedPrazoMes?.descricao || ''}
                                                        onClick={() => toggleListVisibility(setIsPrazoMesListVisible)}
                                                        readOnly
                                                    />
                                                    <button
                                                        className="br-button"
                                                        type="button"
                                                        aria-label="Exibir lista"
                                                        onClick={() => toggleListVisibility(setIsPrazoMesListVisible)}
                                                    >
                                                        <i className="fas fa-angle-down" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                                {isPrazoMesListVisible && (
                                                    <ul className={styles.dropdownList}>
                                                        {prazoMesPagamento.map((prazo) => (
                                                            <li
                                                                key={prazo.valor}
                                                                onClick={() => handleOptionSelect(prazo, setSelectedPrazoMes, setIsPrazoMesListVisible)}
                                                                className={styles.dropdownItem}
                                                            >
                                                                {prazo.descricao}
                                                            </li>
                                                        ))}
                                                    </ul>
                                                )}
                                            </div>
                                        </div>

                                        <div className="br-button-group">
                                            <button className="br-button primary"
                                                type="submit" disabled={isSubmitting}>
                                                {isSubmitting ? 'Enviando...' : 'Enviar Requisição'}
                                            </button>
                                            <button className="br-button secondary" type="button" style={{ marginLeft: '20px' }} onClick={() => navigate("/")}>
                                                Voltar
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                {feedbackMessage && (
                                    <div className={`br-message ${feedbackMessage.includes('Erro') ? 'danger' : 'success'} mt-4`}>
                                        <div className="icon">
                                            <i className={`fas ${feedbackMessage.includes('Erro') ? 'fa-times-circle' : 'fa-check-circle'} fa-lg`} aria-hidden="true"></i>
                                        </div>
                                        <div className="content" role="alert">
                                            {feedbackMessage.includes('Erro') ? (
                                                <>
                                                    <span className="message-title">Erro:</span>
                                                    <span className="message-body"> {feedbackMessage}</span>
                                                </>
                                            ) : (
                                                <>
                                                    <span className="message-title">Sucesso:</span>
                                                    <span className="message-body"> {feedbackMessage}</span>
                                                </>
                                            )}
                                        </div>
                                        <div className="close">
                                            <button className="br-button circle small" type="button" aria-label="Fechar a mensagem de alerta" onClick={handleCloseError}>
                                                <i className="fas fa-times" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                    </div>
                                )}
                            </div>
                        </>
                    )}
                </>
            )}
        </div>
    );
};

export default CadastroRequisicao;
