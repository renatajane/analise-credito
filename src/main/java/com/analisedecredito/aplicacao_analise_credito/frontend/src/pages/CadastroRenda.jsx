import React, { useState, useEffect, useRef } from 'react';
import styles from './CadastroRequisicao.module.css';
import axios from 'axios';

const CadastroRenda = ({ rendas, onAddRenda, onRemoveRenda, onUpdateRenda }) => {
    const [errors, setErrors] = useState({
        valorRenda: '',
        descricaoRendaTipo: ''
    });

    const [tiposRenda, setTiposRenda] = useState([]); // Estado para armazenar tipos de renda
    const [visibleIndex, setVisibleIndex] = useState(null);
    const handleDropdownToggle = (index) => {
        setVisibleIndex(visibleIndex === index ? null : index);
    };

    // Buscar tipos de renda ao montar o componente
    useEffect(() => {
        const fetchTiposRenda = async () => {
            try {
                const response = await axios.get('http://localhost:8080/renda-tipo/list');
                setTiposRenda(response.data);
            } catch (error) {
                console.error('Erro ao buscar tipos de renda:', error);
            }
        };

        fetchTiposRenda();
    }, []);

    const renderRendas = () => {
        return rendas.map((renda, index) => (
            <div key={index} className="renda-container" style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
                <div className="col-sm-20 col-lg-30 mb-2">
                    <label className="text-nowrap" htmlFor={`renda-${index}`}>
                        Tipo de Renda:
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
                                        onClick={() => {
                                            onUpdateRenda(index, tipoRenda.idRendaTipo, 'rendaTipo.idRendaTipo');
                                            onUpdateRenda(index, tipoRenda.descricaoRendaTipo, 'rendaTipo.descricaoRendaTipo');
                                        }}
                                        className={styles.dropdownItem}
                                    >
                                        {tipoRenda.descricaoRendaTipo}
                                    </li>
                                ))}
                            </ul>
                        )}
                    </div>
                </div>
                <div className="br-input" style={{ flex: '0 0 200px', marginLeft: '10px' }}>
                    <label htmlFor={`valorRenda-${index}`} style={{ marginBottom: '5px', display: 'block' }}>valorRenda da Renda</label>
                    <input
                        id={`valorRenda-${index}`}
                        type="text"
                        placeholder="Digite o valorRenda"
                        value={renda.valorRenda}
                        onChange={(event) => onUpdateRenda(index, event.target.value, 'valorRenda')}
                        name="valorRenda"
                        style={{ width: '100%', marginBottom: '13px' }}
                    />
                </div>
                <button
                    className="br-button circle secondary small"
                    type="button"
                    onClick={() => onRemoveRenda(index)}
                    aria-label="Remover renda"
                    style={{
                        marginLeft: '10px',
                        marginTop: '25px',
                        padding: '6px',
                        fontSize: '14px',
                        lineHeight: '1.5',
                        display: 'flex',
                        alignItems: 'center'
                    }}
                >
                    <i className="fas fa-minus" aria-hidden="true"></i>
                </button>
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
                    Adicionar outra renda
                </button>
            </div>
        </div>
    );
};

export default CadastroRenda;
