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
    const [emprestimoObjetivo, setEmprestimoObjetivo] = useState('');
    const [emprestimoUrgencia, setEmprestimoUrgencia] = useState('');

    const [isModalidadeListVisible, setIsModalidadeListVisible] = useState(false);
    const [isObjetivoListVisible, setIsObjetivoListVisible] = useState(false);
    const [isUrgenciaListVisible, setIsUrgenciaListVisible] = useState(false);
    const [isModalidadePagamentoListVisible, setIsModalidadePagamentoListVisible] = useState(false);

    const modalidadeRef = useRef(null);

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

    const handleOptionSelect = (modalidade) => {
        setSelectedModalidade(modalidade);
        console.log('Descrição selecionada:', modalidade.descricaoModalidade);
        setIsModalidadeListVisible(false);
        setIsObjetivoListVisible(false);
        setIsUrgenciaListVisible(false);
        setIsModalidadePagamentoListVisible(false);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const data = {
            cliente: 1, // Substitua conforme necessário
            emprestimoModalidade: selectedModalidade?.idModalide,
            valorRequerido,
            dataRequisicao,
            emprestimoObjetivo,
            emprestimoUrgencia,
            modalidadePagamento,
            diaPagamento,
            prazoMes
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
                {/* Outros campos aqui... */}
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
                                        key={modalidade.idModalide}
                                        onClick={() => handleOptionSelect(modalidade)}
                                        className={styles.dropdownItem}
                                    >
                                        {modalidade.descricaoModalidade}
                                    </li>
                                ))}
                            </ul>
                        )}
                    </div>
                </div>
                {/* Outros campos aqui... */}
            </div>

            <button className="br-button primary" type="submit">
                Enviar
            </button>
        </form>
    );
};

export default CadastroRequisicao;
