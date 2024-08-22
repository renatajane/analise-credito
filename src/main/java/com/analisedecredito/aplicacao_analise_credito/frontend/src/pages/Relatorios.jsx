import React, { useState } from 'react';
import axios from 'axios';
import { cpfMask } from '../components/Mask/CpfMask';
import './Relatorios.css'; 

const Relatorios = () => {
  const [requisicoes, setRequisicoes] = useState([]);
  const [cpf, setCpf] = useState('');
  const [idRequisicao, setIdRequisicao] = useState('');
  const [inicio, setInicio] = useState('');
  const [fim, setFim] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [showList, setShowList] = useState(false);
  const [showDateInputs, setShowDateInputs] = useState(false);
  const [expandedItem, setExpandedItem] = useState(null); // Para controlar qual item está expandido

  const handleSearch = () => {
    const cpfNumerico = cpf.replace(/\D/g, '');
    if (cpfNumerico.length === 11) {
      setLoading(true);
      setError(null);

      axios.get(`http://localhost:8080/emprestimo-requisicao/list-by-cpf?cpf=${cpfNumerico}`)
        .then(response => {
          const data = Array.isArray(response.data) ? response.data : [];
          setRequisicoes(data);
          setShowList(data.length > 0); // Mostra a lista se houver requisições
          if (data.length === 0) {
            setError('Nenhuma requisição encontrada.');
          }
        })
        .catch(err => {
          setError('Erro ao buscar requisições.');
          setShowList(false);
        })
        .finally(() => setLoading(false));
    } else {
      setError('CPF inválido. Verifique o formato e tente novamente.');
      setShowList(false);
    }
  };

  const handleGeneratePdfById = () => {
    const cpfNumerico = cpf.replace(/\D/g, '');
    if (idRequisicao && cpfNumerico) {
      window.open(`http://localhost:8080/emprestimo-requisicao/pdf/${cpfNumerico}/${idRequisicao}`, '_blank');
    } else {
      setError('Você precisa selecionar uma requisição.');
    }
  };

  const handleGeneratePdfByPeriod = () => {
    if (inicio && fim) {
      window.open(`http://localhost:8080/emprestimo-requisicao/pdf/periodo?inicio=${inicio}&fim=${fim}`, '_blank');
    } else {
      setError('Datas de início e fim são necessárias.');
    }
  };

  const handleCpfChange = (e) => {
    setCpf(cpfMask(e.target.value));
    // Esconde a lista e limpa as requisições ao mudar o CPF
    setShowList(false);
    setRequisicoes([]);
    setShowDateInputs(false); // Esconde os inputs de data
    setError(null); // Limpa qualquer mensagem de erro anterior
  };

  const handleSelectId = (id) => {
    setIdRequisicao(id);
    setShowDateInputs(false); // Esconde os inputs de data quando um ID é selecionado
  };

  const handleBackToCpf = () => {
    setCpf('');
    setRequisicoes([]);
    setShowList(false);
    setShowDateInputs(false);
    setError(null);
  };

  const handleToggleExpand = (id) => {
    setExpandedItem(expandedItem === id ? null : id);
  };

  return (
    <div>
      {!showList && !showDateInputs && (
        <div>
          <label htmlFor="cpf">CPF:</label>
          <input
            id="cpf"
            type="text"
            placeholder="Digite o CPF"
            value={cpf}
            onChange={handleCpfChange}
            maxLength={14} // Limita o input a 14 caracteres
          />
          <button onClick={handleSearch}>Buscar Requisições</button>
        </div>
      )}

      {loading && <p>Carregando...</p>}
      {error && <p>{error}</p>}

      {showList && requisicoes.length > 0 && (
        <div className="br-list" role="list">
          <div className="header">
            <div className="title">Requisições</div>
          </div>
          <span className="br-divider"></span>
          {requisicoes.map((req) => (
            <div key={req.idRequisicao}>
              <div
                className="br-item"
                role="listitem"
                onClick={() => handleToggleExpand(req.idRequisicao)}
              >
                <div className="content">
                  <div className="flex-fill">Requisição ID: {req.idRequisicao}</div>
                  <i className={`fas ${expandedItem === req.idRequisicao ? 'fa-angle-up' : 'fa-angle-down'}`} aria-hidden="true"></i>
                </div>
              </div>
              <div className={`br-list ${expandedItem === req.idRequisicao ? '' : 'hidden'}`} role="list">
                <div className="br-item" role="listitem">
                  <div className="row align-items-center">
                    <div className="col-auto"><i className="fas fa-heartbeat" aria-hidden="true"></i></div>
                    <div className="col">Descrição: {req.descricaoResultado}</div>
                  </div>
                </div>
                <span className="br-divider"></span>
                <div className="br-item" role="listitem">
                  <div className="row align-items-center">
                    <div className="col-auto"><i className="fas fa-calendar-day" aria-hidden="true"></i></div>
                    <div className="col">Data: {req.dataRequisicao}</div>
                  </div>
                </div>
                {/* Adicione mais detalhes conforme necessário */}
              </div>
            </div>
          ))}
          <div>
            <button onClick={handleGeneratePdfById}>Gerar PDF da Requisição Selecionada</button>
            <button onClick={() => setShowDateInputs(true)}>Gerar Relatório por Período</button>
            <button onClick={handleBackToCpf}>Consultar Novo CPF</button>
          </div>
        </div>
      )}

      {showDateInputs && (
        <div>
          <h4>Gerar Relatório por Período</h4>
          <label htmlFor="inicio">Data de Início:</label>
          <input
            id="inicio"
            type="date"
            value={inicio}
            onChange={(e) => setInicio(e.target.value)}
          />
          <label htmlFor="fim">Data de Fim:</label>
          <input
            id="fim"
            type="date"
            value={fim}
            onChange={(e) => setFim(e.target.value)}
          />
          <button onClick={handleGeneratePdfByPeriod}>Gerar PDF por Período</button>
          <button onClick={handleBackToCpf}>Consultar Novo CPF</button>
        </div>
      )}
    </div>
  );
};

export default Relatorios;
