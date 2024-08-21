import React, { useState } from 'react';
import axios from 'axios';

const ClienteList = () => {
    const [cpf, setCpf] = useState('');
    const [requisicoes, setRequisicoes] = useState([]);
    const [selectedRequisicao, setSelectedRequisicao] = useState(null);

    const handleCpfChange = (event) => {
        setCpf(event.target.value);
    };

    const handleSearch = async () => {
        try {
            const data = await fetchRequisicoesByCpf(cpf);
            setRequisicoes(data);
        } catch (error) {
            console.error("Erro ao buscar requisições:", error);
        }
    };

    const handleDownloadPdf = async () => {
        if (selectedRequisicao) {
            try {
                await downloadPdfByCpfAndId(cpf, selectedRequisicao.id);
            } catch (error) {
                console.error("Erro ao baixar PDF:", error);
            }
        } else {
            alert("Selecione uma requisição primeiro.");
        }
    };

    return (
        <div>
            <input
                type="text"
                value={cpf}
                onChange={handleCpfChange}
                placeholder="Digite o CPF"
            />
            <button onClick={handleSearch}>Buscar Requisições</button>

            <ul>
                {requisicoes.map((req) => (
                    <li key={req.id}>
                        <input
                            type="radio"
                            name="requisicao"
                            value={req.id}
                            onChange={() => setSelectedRequisicao(req)}
                        />
                        {req.descricao}
                    </li>
                ))}
            </ul>

            <button onClick={handleDownloadPdf}>Gerar PDF</button>
        </div>
    );
};

export default ClienteList;
