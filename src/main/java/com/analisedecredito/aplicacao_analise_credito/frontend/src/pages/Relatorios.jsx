import React, { useState } from 'react';
import axios from 'axios';
import EmprestimoRequisicaoList from './EmprestimoRequisicaoList';

const Relatorios = () => {
  const [requisicoes, setRequisicoes] = useState([]);
  const [cpf, setCpf] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSearch = () => {
    if (cpf && cpf.length === 11) { // Exemplo de validação para CPF
      setLoading(true);
      setError(null); // Limpar o erro antes da nova requisição
      axios.get(`/emprestimo-requisicao/list-by-cpf?cpf=${cpf}`)
        .then(response => {
          console.log('Resposta da API:', response.data);
          setRequisicoes(Array.isArray(response.data) ? response.data : []);
        })
        .catch(err => {
          setError('Erro ao buscar requisições.');
          console.error('Erro ao buscar requisições:', err);
        })
        .finally(() => setLoading(false));
    } else {
      setError('CPF inválido. Verifique o formato e tente novamente.');
    }
  };

  const handlePrint = (id) => {
    window.open(`/emprestimo-requisicao/pdf/${cpf}/${id}`, '_blank');
  };

  return (
    <div>
      <label htmlFor="cpf">CPF:</label>
      <input
        id="cpf"
        type="text"
        placeholder="Digite o CPF"
        value={cpf}
        onChange={(e) => setCpf(e.target.value)}
      />
      <button onClick={handleSearch}>Buscar Requisições</button>

      {loading && <p>Carregando...</p>}
      {error && <p>{error}</p>}
      {requisicoes.length === 0 && !loading && !error && <p>Nenhuma requisição encontrada.</p>}

      <EmprestimoRequisicaoList
        requisicoes={requisicoes}
        onPrint={handlePrint}
        
      />
    </div>
  );
};

export default Relatorios;
