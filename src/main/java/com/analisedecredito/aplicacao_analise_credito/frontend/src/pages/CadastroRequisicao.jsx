import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import styles from './CadastroRequisicao.module.css';

const CadastroRequisicao = () => {
    const [valorRequerido, setValorRequerido] = useState('');
    const [dataRequisicao, setDataRequisicao] = useState('');
    const [diaPagamento, setDiaPagamento] = useState('');
    const [prazoMes, setPrazoMes] = useState('');
    const [modalidades, setModalidades] = useState([]);
    const [objetivos, setObjetivos] = useState([]);
    const [urgencias, setUrgencias] = useState([]);
    const [modalidadePagamento, setModalidadePagamento] = useState(1);

    const [selectedModalidade, setSelectedModalidade] = useState(null);
    const [selectedObjetivo, setSelectedObjetivo] = useState(null);
    const [selectedUrgencia, setSelectedUrgencia] = useState(null);
    const [selectedDiaPagamento, setSelectedDiaPagamento] = useState(null);
    const [selectedPrazoMes, setSelectedPrazoMes] = useState(null);

    const [isModalidadeListVisible, setIsModalidadeListVisible] = useState(false);
    const [isObjetivoListVisible, setIsObjetivoListVisible] = useState(false);
    const [isUrgenciaListVisible, setIsUrgenciaListVisible] = useState(false);
    const [isDiaPagamentoListVisible, setIsDiaPagamentoListVisible] = useState(false);
    const [isPrazoMesListVisible, setIsPrazoMesListVisible] = useState(false);

    const modalidadeRef = useRef(null);
    const objetivoRef = useRef(null);
    const urgenciaRef = useRef(null);
    const diaPagamentoRef = useRef(null);
    const prazoMesRef = useRef(null);

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
        const fetchData = async () => {
            try {
                const [modalidadesRes, objetivosRes, urgenciasRes] = await Promise.all([
                    axios.get('http://localhost:8080/emprestimo-modalidade/list'),
                    axios.get('http://localhost:8080/emprestimo-objetivo/list'),
                    axios.get('http://localhost:8080/emprestimo-urgencia/list')
                ]);
                setModalidades(modalidadesRes.data);
                setObjetivos(objetivosRes.data);
                setUrgencias(urgenciasRes.data);
            } catch (error) {
                console.error('Erro ao carregar os dados:', error);
            }
        };
        fetchData();
    }, []);

    const toggleListVisibility = (setter) => setter(prev => !prev);

    const handleOptionSelect = (option, setter, setVisibility) => {
        setter(option);
        setVisibility(false);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const data = {
            cliente: 1, // Substitua conforme necessário
            emprestimoModalidade: selectedModalidade?.idModalidade,
            valorRequerido,
            dataRequisicao,
            emprestimoObjetivo: selectedObjetivo?.idObjetivo,
            emprestimoUrgencia: selectedUrgencia?.idUrgencia,
            modalidadePagamento,
            diaPagamento: selectedDiaPagamento?.valor, // Usando valor numérico selecionado
            prazoMes: selectedPrazoMes?.valor // Usando valor numérico selecionado
        };

        try {
            const response = await axios.post('http://localhost:8080/emprestimo-requisicao', data);
            console.log('Resposta da API:', response.data);
        } catch (error) {
            console.error('Erro ao enviar a requisição:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div className="row">
                <div className="col-sm-8 col-lg-5">
                    <div className="br-input">
                        <label htmlFor="valorRequerido">Valor Requerido</label>
                        <input
                            id="valorRequerido"
                            type="text"
                            placeholder="Digite o valor requerido"
                            value={valorRequerido}
                            onChange={(e) => setValorRequerido(e.target.value)}
                        />
                    </div>
                </div>

                {/* Modalidade de Empréstimo */}
                <div className="col-sm-20 col-lg-30 mb-2">
                    <label className="text-nowrap" htmlFor="modalidade">
                        Modalidade de Empréstimo:
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
                                {modalidades.map(modalidade => (
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
                        Objetivo do Empréstimo:
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
                                {objetivos.map(objetivo => (
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

                {/* Urgência do Empréstimo */}
                <div className="col-sm-20 col-lg-30 mb-2">
                    <label className="text-nowrap" htmlFor="urgencia">
                        Urgência do Empréstimo:
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
                                {urgencias.map(urgencia => (
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

                {/* Dia de Pagamento */}
                <div className="col-sm-20 col-lg-30 mb-2">
                    <label className="text-nowrap" htmlFor="diaPagamento">
                        Dia de Pagamento:
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
                                {diasPagamento.map(dia => (
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

                {/* Prazo de Pagamento em Meses */}
                <div className="col-sm-20 col-lg-30 mb-2">
                    <label className="text-nowrap" htmlFor="prazoMes">
                        Prazo de Pagamento em Meses:
                    </label>
                    <div className={styles.brselect} ref={prazoMesRef}>
                        <div className="br-input">
                            <input
                                id="prazoMes"
                                type="text"
                                className="br-input"
                                placeholder="Selecione o prazo em meses"
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
                                {prazoMesPagamento.map(prazo => (
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

                {/* Data da Requisição */}
                <div className="col-sm-8 col-lg-5">
                    <div className="br-input">
                        <label htmlFor="dataRequisicao">Data da Requisição</label>
                        <input
                            id="dataRequisicao"
                            type="date"
                            value={dataRequisicao}
                            onChange={(e) => setDataRequisicao(e.target.value)}
                        />
                    </div>
                </div>

                {/* Botão de Enviar */}
                <div className="col-sm-12 col-lg-6">
                    <button type="submit" className="br-button">Enviar</button>
                </div>
            </div>
        </form>
    );
};

export default CadastroRequisicao;
