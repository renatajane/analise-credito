import React, { useState, useEffect } from 'react';
import styles from './CadastroRequisicao.module.css';
import { ApiService } from '../services/appService';

const CadastroPatrimonio = ({ patrimonios, onAddPatrimonio, onRemovePatrimonio, onUpdatePatrimonio }) => {
    const [errors, setErrors] = useState({
        valor: '',
        descricaoPatrimonioTipo: ''
    });

    const [tiposPatrimonio, setTiposPatrimonio] = useState([]);
    const [visibleIndex, setVisibleIndex] = useState(null);
    const handleDropdownToggle = (index) => {
        setVisibleIndex(visibleIndex === index ? null : index);
    };

    // Buscar tipos de patrimonio ao montar o componente
    useEffect(() => {
        const fetchTiposPatrimonio = async () => {
            try {
                const response = await ApiService.Get('patrimonio-tipo/list');
                setTiposPatrimonio(response.data);

            } catch (error) {
                console.error('Erro ao buscar tipos de patrimonio:', error);
            }
        };

        fetchTiposPatrimonio();
    }, []);


    const renderPatrimonios = () => {
        return patrimonios.map((patrimonio, index) => (
            <div key={index} className="patrimonio-container" style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
                <div className="col-sm-20 col-lg-30 mb-2">
                    <label className="text-nowrap" htmlFor={`patrimonio-${index}`}>
                        Tipo de Patrimônio:
                    </label>
                    <div className={styles.brselect}>
                        <div className="br-input">
                            <input
                                id={`patrimonio-${index}`}
                                type="text"
                                className="br-input"
                                placeholder="Selecione um Tipo de Patrimônio"
                                value={patrimonio.patrimonioTipo.descricaoPatrimonioTipo || ''}
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
                                {tiposPatrimonio.map(tipoPatrimonio => (
                                    <li
                                        key={tipoPatrimonio.idPatrimonioTipo}
                                        onClick={() => {
                                            onUpdatePatrimonio(index, tipoPatrimonio.idPatrimonioTipo, 'patrimonioTipo.idPatrimonioTipo');
                                            onUpdatePatrimonio(index, tipoPatrimonio.descricaoPatrimonioTipo, 'patrimonioTipo.descricaoPatrimonioTipo');
                                        }}
                                        className={styles.dropdownItem}
                                    >
                                        {tipoPatrimonio.descricaoPatrimonioTipo}
                                    </li>
                                ))}
                            </ul>
                        )}
                    </div>
                </div>
                <div className="br-input" style={{ flex: '0 0 200px', marginLeft: '10px' }}>
                    <label htmlFor={`valor-${index}`} style={{ marginBottom: '5px', display: 'block' }}>Valor do Patrimônio</label>
                    <input
                        id={`valor-${index}`}
                        type="number"
                        placeholder="Digite o valor"
                        value={patrimonio.valorPatrimonio}
                        onChange={(event) => onUpdatePatrimonio(index, event.target.value, "valorPatrimonio")}
                        style={{ width: '100%', marginBottom: '13px' }}
                    />
                </div>

                <button
                    className="br-button circle secondary small"
                    type="button"
                    onClick={() => onRemovePatrimonio(index)}
                    aria-label="Remover patrimônio"
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
                {errors.valor && <div className="error-message">{errors.valor}</div>}
                {errors.descricaoPatrimonioTipo && <div className="error-message">{errors.descricaoPatrimonioTipo}</div>}
            </div>
        ));
    };

    return (
        <div>
            {renderPatrimonios()}
            <div className="p-3">
                <button
                    className="br-button secondary"
                    type="button"
                    onClick={onAddPatrimonio}
                    aria-label="Adicionar novo patrimônio"
                >
                    Adicionar patrimônio
                </button>
            </div>
        </div>
    );
};

export default CadastroPatrimonio;
