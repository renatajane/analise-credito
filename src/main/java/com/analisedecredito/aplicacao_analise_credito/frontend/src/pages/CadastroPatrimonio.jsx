import React, { useState, useEffect } from 'react';
import axios from 'axios';

const CadastroPatrimonio = ({ id }) => {
    const [patrimonios, setPatrimonios] = useState([
        { idPatrimonioTipo: '', descricaoPatrimonioTipo: '', valor: '' }
    ]);
    const [tipoPatrimonioOptions, setTipoPatrimonioOptions] = useState([]);
    const [errors, setErrors] = useState({
        valor: '',
        descricaoPatrimonioTipo: ''
    });

    useEffect(() => {
        // Fetch tipo de patrimonio options
        const fetchTipoPatrimonioOptions = async () => {
            try {
                const response = await axios.get('http://localhost:8080/tipo-patrimonio/list');
                setTipoPatrimonioOptions(response.data);
            } catch (error) {
                console.error('Erro ao buscar opções de tipo de patrimônio:', error);
            }
        };

        fetchTipoPatrimonioOptions();
    }, []);

    useEffect(() => {
        if (id != null) {
            const fetchData = async () => {
                try {
                    const response = await axios.get(`http://localhost:8080/patrimonio/idCliente/${id}`);
                    setPatrimonios([
                        {
                            idPatrimonioTipo: response.data.idPatrimonioTipo,
                            descricaoPatrimonioTipo: response.data.descricaoPatrimonioTipo,
                            valor: response.data.valor || ''
                        }
                    ]);
                } catch (error) {
                    console.error('Erro ao buscar os dados do patrimônio:', error);
                }
            };

            fetchData();
        }
    }, [id]);

    const addPatrimonio = () => {
        setPatrimonios([...patrimonios, { idPatrimonioTipo: '', descricaoPatrimonioTipo: '', valor: '' }]);
    };

    const removePatrimonio = (index) => {
        setPatrimonios(patrimonios.filter((_, i) => i !== index));
    };

    const handleInputChange = (index, event) => {
        const { name, value } = event.target;
        const newPatrimonios = [...patrimonios];
        newPatrimonios[index][name] = value;
        setPatrimonios(newPatrimonios);
    };

    const renderPatrimonios = () => {
        return patrimonios.map((patrimonio, index) => (
            <div key={index} className="patrimonio-container" style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
                <div className="br-select" style={{ flex: '1' }}>
                    <div className="br-input" style={{ marginBottom: '0' }}>
                        <label htmlFor={`select-patrimonio-${index}`} style={{ marginBottom: '5px', display: 'block' }}>Tipo de Patrimônio</label>
                        <input
                            id={`select-patrimonio-${index}`}
                            type="text"
                            placeholder="Selecione o item"
                            value={patrimonio.descricaoPatrimonioTipo}
                            onChange={(event) => handleInputChange(index, event)}
                            name="descricaoPatrimonioTipo"
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
                    <label htmlFor={`valor-${index}`} style={{ marginBottom: '5px', display: 'block' }}>Valor do Patrimônio</label>
                    <input
                        id={`valor-${index}`}
                        type="text"
                        placeholder="Digite o valor"
                        value={patrimonio.valor}
                        onChange={(event) => handleInputChange(index, event)}
                        name="valor"
                        style={{ width: '100%' }}
                    />
                </div>
                <button
                    className="br-button circle secondary small"
                    type="button"
                    onClick={() => removePatrimonio(index)}
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
                    onClick={addPatrimonio}
                    aria-label="Adicionar novo patrimônio"
                >
                    Adicionar outro patrimônio
                </button>
            </div>
        </div>
    );
};

export default CadastroPatrimonio;
