import React, { useState, useEffect } from 'react';
import styles from './CadastroRequisicao.module.css';
import { ApiService } from '../services/appService';

const CadastroRenda = ({ rendas, onAddRenda, onRemoveRenda, onUpdateRenda }) => {
    const [errors, setErrors] = useState({
        valorRenda: '',
        descricaoRendaTipo: ''
    });

    const [tiposRenda, setTiposRenda] = useState([]);
    const [visibleIndex, setVisibleIndex] = useState(null);

    useEffect(() => {
        const fetchTiposRenda = async () => {
            try {
                const response = await ApiService.Get('renda-tipo/list');
                setTiposRenda(response.data);
            } catch (error) {
                console.error('Erro ao buscar tipos de renda:', error);
            }
        };

        fetchTiposRenda();
    }, []);

    const handleDropdownToggle = (index) => {
        setVisibleIndex(visibleIndex === index ? null : index);
    };

    const handleTipoRendaSelect = (index, tipoRenda) => {
        onUpdateRenda(index, tipoRenda.idRendaTipo, 'rendaTipo.idRendaTipo');
        onUpdateRenda(index, tipoRenda.descricaoRendaTipo, 'rendaTipo.descricaoRendaTipo');
        setVisibleIndex(null); // Fecha o dropdown após a seleção
    };

    const renderRendas = () => {
        return rendas.map((renda, index) => (
            <div key={index} className="renda-container" style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
                <div className="col-sm-20 col-lg-30 mb-2">
                    <label className="text-nowrap" htmlFor={`renda-${index}`}>
                        Tipo de Renda
                    </label>
                    <div className={styles.brselect}>
                        <div className="br-input">
                            <input
                                id={`renda-${index}`}
                                type="text"
                                className="br-input"
                                placeholder="Selecione um tipo de renda"
                                value={renda.rendaTipo.descricaoRendaTipo || ''}
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
                                {tiposRenda.map(tipoRenda => (
                                    <li
                                        key={tipoRenda.idRendaTipo}
                                        onClick={() => handleTipoRendaSelect(index, tipoRenda)}
                                        className={styles.dropdownItem}
                                    >
                                        {tipoRenda.descricaoRendaTipo}
                                    </li>
                                ))}
                            </ul>
                        )}
                    </div>
                </div>
                <div className="br-input" style={{ flex: '0 0 200px', marginLeft: '20px' }}>
                    <label htmlFor={`valorRenda-${index}`} style={{ marginBottom: '5px', display: 'block' }}>Valor da Renda</label>
                    <input
                        id={`valorRenda-${index}`}
                        type="text"
                        placeholder="Digite o Valor da Renda"
                        value={renda.valorRenda}
                        onChange={(event) => onUpdateRenda(index, event.target.value, 'valorRenda')}
                        name="valorRenda"
                        style={{ width: '100%', marginBottom: '13px' }}
                    />
                </div>
                <div className="p-3">
                    <button
                        className="br-button"
                        type="button"
                        onClick={() => onRemoveRenda(index)}
                        aria-label="Remover renda"
                        style={{ marginTop: '15px' }}
                    >
                        Remover
                    </button>
                </div>
                {errors.valorRenda && <div className="error-message">{errors.valorRenda}</div>}
                {errors.descricaoRendaTipo && <div className="error-message">{errors.descricaoRendaTipo}</div>}
            </div>
        ));
    };

    return (
        <div>
            {renderRendas()}
            <div className="p-3">
                <button
                    className="br-button secondary"
                    type="button"
                    onClick={onAddRenda}
                    aria-label="Adicionar nova renda"
                >
                    Adicionar renda
                </button>
            </div>
        </div>
    );
};

export default CadastroRenda;
