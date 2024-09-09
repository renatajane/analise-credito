import React, { useState, useEffect } from 'react';
import styles from './CadastroRequisicao.module.css';
import { ApiService } from '../services/appService';

const CadastroDespesa = ({ despesas, onAddDespesa, onRemoveDespesa, onUpdateDespesa }) => {
    const [errors, setErrors] = useState({
        valor: '',
        descricaoDespesaTipo: ''
    });
    const [tiposDespesa, setTiposDespesa] = useState([]);
    const [visibleIndex, setVisibleIndex] = useState(null);

    useEffect(() => {
        const fetchTiposDespesa = async () => {
            try {
                const response = await ApiService.Get('despesa-tipo/list');
                setTiposDespesa(response.data);
            } catch (error) {
                console.error('Erro ao buscar tipos de despesa:', error);
            }
        };

        fetchTiposDespesa();
    }, []);

    const handleDropdownToggle = (index) => {
        setVisibleIndex(visibleIndex === index ? null : index);
    };

    const handleTipoDespesaSelect = (index, tipoDespesa) => {
        onUpdateDespesa(index, tipoDespesa.idDespesaTipo, 'despesaTipo.idDespesaTipo');
        onUpdateDespesa(index, tipoDespesa.descricaoDespesaTipo, 'despesaTipo.descricaoDespesaTipo');
        setVisibleIndex(null); // Fecha o dropdown após a seleção
    };

    const renderDespesas = () => {
        return despesas.map((despesa, index) => (
            <div key={index} className="despesa-container" style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
                <div className="col-sm-20 col-lg-30 mb-2">
                    <label className="text-nowrap" htmlFor={`despesa-${index}`}>
                        Tipo de Despesa:
                    </label>
                    <div className={styles.brselect}>
                        <div className="br-input">
                            <input
                                id={`despesa-${index}`}
                                type="text"
                                className="br-input"
                                placeholder="Selecione um Tipo de Despesa"
                                value={despesa.despesaTipo.descricaoDespesaTipo || ''}
                                onClick={() => handleDropdownToggle(index)}
                                readOnly
                            />
                            <button
                                className="br-button"
                                type="button"
                                aria-label="Exibir lista"
                                style={{ marginLeft: '220px' }}
                                onClick={() => handleDropdownToggle(index)}
                            >
                                <i className="fas fa-angle-down" aria-hidden="true"></i>
                            </button>
                        </div>
                        {visibleIndex === index && (
                            <ul className={styles.dropdownList}>
                                {tiposDespesa.map(tipoDespesa => (
                                    <li
                                        key={tipoDespesa.idDespesaTipo}
                                        onClick={() => handleTipoDespesaSelect(index, tipoDespesa)}
                                        className={styles.dropdownItem}
                                    >
                                        {tipoDespesa.descricaoDespesaTipo}
                                    </li>
                                ))}
                            </ul>
                        )}
                    </div>
                </div>
                <div className="br-input" style={{ flex: '0 0 200px', marginLeft: '20px' }}>
                    <label htmlFor={`valor-${index}`} style={{ marginBottom: '5px', display: 'block' }}>Valor da Despesa</label>
                    <input
                        id={`valor-${index}`}
                        type="text"
                        placeholder="Digite o valor"
                        onChange={(event) => onUpdateDespesa(index, event.target.value, "valorDespesa")}
                        value={despesa.valorDespesa}
                        style={{ width: '100%', marginBottom: '15px' }}
                    />
                </div>
                <div className="p-3">
                    <button
                        className="br-button"
                        type="button"
                        onClick={() => onRemoveDespesa(index)}
                        aria-label="Remover despesa"
                        style={{ marginTop: '15px' }}
                    >
                        Remover
                    </button>
                </div>
                {errors.valor && <div className="error-message">{errors.valor}</div>}
                {errors.descricaoDespesaTipo && <div className="error-message">{errors.descricaoDespesaTipo}</div>}
            </div>
        ));
    };

    return (
        <div>
            {renderDespesas()}
            <div className="p-3">
                <button
                    className="br-button secondary"
                    type="button"
                    onClick={onAddDespesa}
                    aria-label="Adicionar nova despesa"
                >
                    Adicionar despesa
                </button>
            </div>
        </div>
    );
};

export default CadastroDespesa;
