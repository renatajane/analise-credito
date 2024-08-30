import React, { useState, useEffect } from 'react';
import axios from 'axios';

const CadastroRenda = ({ id }) => {
    const [rendas, setRendas] = useState([
        { idRendaFonte: '', descricaoRendaTipo: '', valor: '' }
    ]);
    const [errors, setErrors] = useState({
        valor: '',
        descricaoRendaTipo: ''
    });

    useEffect(() => {
        if (id != null) {
            const fetchData = async () => {
                try {
                    const response = await axios.get(`http://localhost:8080/renda-fonte/${id}`);
                    console.log("MEUS DADOSOOOSSSS*** ", response.data);
                    console.log("valor da minha renda", response.data.valorRenda);
                    setRendas([
                        {
                            idRendaFonte: response.data.rendaFonte,
                            descricaoRendaTipo: response.data.rendaTipo.descricaoRendaTipo,
                            valor: '' // Ajuste conforme necessário
                        }
                    ]);
                } catch (error) {
                    console.error('Erro ao buscar os dados da renda:', error);
                }
            };

            fetchData();
        }
    }, [id]);

    const addRenda = () => {
        setRendas([...rendas, { idRendaFonte: '', descricaoRendaTipo: '', valor: '' }]);
    };

    const removeRenda = (index) => {
        setRendas(rendas.filter((_, i) => i !== index));
    };

    const handleInputChange = (index, event) => {
        const { name, value } = event.target;
        const newRendas = [...rendas];
        newRendas[index][name] = value;
        setRendas(newRendas);
    };

    const renderRendas = () => {
        return rendas.map((renda, index) => (
            <div key={index} className="renda-container" style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
                <div className="br-select" style={{ flex: '1' }}>
                    <div className="br-input" style={{ marginBottom: '0' }}>
                        <label htmlFor={`select-simple-${index}`} style={{ marginBottom: '5px', display: 'block' }}>Tipo de Renda</label>
                        <input
                            id={`select-simple-${index}`}
                            type="text"
                            placeholder="Selecione o item"
                            value={renda.descricaoRendaTipo}
                            onChange={(event) => handleInputChange(index, event)}
                            name="descricaoRendaTipo"
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
                    <label htmlFor={`valor-${index}`} style={{ marginBottom: '5px', display: 'block' }}>Valor da Renda</label>
                    <input
                        id={`valor-${index}`}
                        type="text"
                        placeholder="Digite o valor"
                        value={renda.valorRenda}
                        onChange={(event) => handleInputChange(index, event)}
                        name="valor"
                        style={{ width: '100%' }}
                    />
                </div>
                <button
                    className="br-button circle secondary small"
                    type="button"
                    onClick={() => removeRenda(index)}
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
                {errors.valor && <div className="error-message">{errors.valor}</div>}
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
                    onClick={addRenda}
                    aria-label="Adicionar nova renda"
                >
                    Adicionar outra renda
                </button>
            </div>
        </div>
    );
};

export default CadastroRenda;
