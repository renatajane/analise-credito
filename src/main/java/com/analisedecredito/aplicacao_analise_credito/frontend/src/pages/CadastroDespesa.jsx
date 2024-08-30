import React, { useState, useEffect } from 'react';
import axios from 'axios';

const CadastroDespesa = ({ id }) => {
    const [despesas, setDespesas] = useState([
        { idDespesaTipo: '', descricaoDespesaTipo: '', valor: '' }
    ]);
    const [tipoDespesaOptions, setTipoDespesaOptions] = useState([]);
    const [errors, setErrors] = useState({
        valor: '',
        descricaoDespesaTipo: ''
    });

    useEffect(() => {
        // Fetch tipo de despesa options
        const fetchTipoDespesaOptions = async () => {
            try {
                const response = await axios.get('http://localhost:8080/tipo-despesa');
                setTipoDespesaOptions(response.data);
            } catch (error) {
                console.error('Erro ao buscar opções de tipo de despesa:', error);
            }
        };

        fetchTipoDespesaOptions();
    }, []);

    useEffect(() => {
        if (id != null) {
            const fetchData = async () => {
                try {
                    const response = await axios.get(`http://localhost:8080/despesa/idCliente/${id}`);
                    setDespesas([
                        {
                            idDespesaTipo: response.data.idDespesaTipo,
                            descricaoDespesaTipo: response.data.descricaoDespesaTipo,
                            valor: response.data.valor || ''
                        }
                    ]);
                } catch (error) {
                    console.error('Erro ao buscar os dados da despesa:', error);
                }
            };

            fetchData();
        }
    }, [id]);

    const addDespesa = () => {
        setDespesas([...despesas, { idDespesaTipo: '', descricaoDespesaTipo: '', valor: '' }]);
    };

    const removeDespesa = (index) => {
        setDespesas(despesas.filter((_, i) => i !== index));
    };

    const handleInputChange = (index, event) => {
        const { name, value } = event.target;
        const newDespesas = [...despesas];
        newDespesas[index][name] = value;
        setDespesas(newDespesas);
    };

    const renderDespesas = () => {
        return despesas.map((despesa, index) => (
            <div key={index} className="despesa-container" style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
                <div className="br-select" style={{ flex: '1' }}>
                    <div className="br-input" style={{ marginBottom: '0' }}>
                        <label htmlFor={`select-despesa-${index}`} style={{ marginBottom: '5px', display: 'block' }}>Tipo de Despesa</label>
                        <input
                            id={`select-despesa-${index}`}
                            type="text"
                            placeholder="Selecione o item"
                            value={despesa.descricaoDespesaTipo}
                            onChange={(event) => handleInputChange(index, event)}
                            name="descricaoDespesaTipo"
                            style={{ width: '100%' }}
                        />
                        <button
                            className="br-button"
                            type="button"
                            aria-label="Exibir lista"
                            tabIndex="-1"
                            data-trigger="data-trigger"
                        >
                            <i className="fas fa-angle-down" aria-hidden="true"></i>
                        </button>
                    </div>
                </div>
                <div className="br-input" style={{ flex: '0 0 200px', marginLeft: '10px' }}>
                    <label htmlFor={`valor-${index}`} style={{ marginBottom: '5px', display: 'block' }}>Valor da Despesa</label>
                    <input
                        id={`valor-${index}`}
                        type="text"
                        placeholder="Digite o valor"
                        value={despesa.valor}
                        onChange={(event) => handleInputChange(index, event)}
                        name="valor"
                        style={{ width: '100%' }}
                    />
                </div>
                <button
                    className="br-button circle secondary small"
                    type="button"
                    onClick={() => removeDespesa(index)}
                    aria-label="Remover despesa"
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
                    onClick={addDespesa}
                    aria-label="Adicionar nova despesa"
                >
                    Adicionar outra despesa
                </button>
            </div>
        </div>
    );
};

export default CadastroDespesa;
