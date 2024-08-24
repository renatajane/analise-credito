import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const ListarRequisicoes = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [requisicoes, setRequisicoes] = useState([]);
  const [idRequisicao, setIdRequisicao] = useState('');
  const [inicio, setInicio] = useState('');
  const [fim, setFim] = useState('');
  const [expandedItem, setExpandedItem] = useState(null);
  const [showInput, setShowInput] = useState(false);
  const [inputId, setInputId] = useState('');
  const cpf = location.state?.cpf;  // Extraindo o CPF do estado

  useEffect(() => {
    if (location.state && location.state.requisicoes) {
      setRequisicoes(location.state.requisicoes);
    } else {
      // Redirecionar para a página de busca se não houver estado
      navigate('/buscarRequisicao');
    }
  }, [location.state, navigate]);

  const handleGeneratePdfById = () => {
    setShowInput(true);
  };

  const handleInputChange = (e) => {
    setInputId(e.target.value);
  };

  const handleGeneratePdf = () => {
    if (inputId && cpf) {
      window.open(`http://localhost:8080/emprestimo-requisicao/pdf/${cpf}/${inputId}`, '_blank');
      setShowInput(false);
      setInputId('');
    } else {
      alert('Você precisa inserir um ID e garantir que o CPF esteja disponível.');
    }
  };

  const handleSearchAnotherCpf = () => {
    navigate('/buscarRequisicao'); // Redireciona para a tela de busca de requisições
  };

  const handleGeneratePdfByPeriod = () => {
    if (inicio && fim && cpf) {
      window.open(`http://localhost:8080/emprestimo-requisicao/pdf/periodo?inicio=${inicio}&fim=${fim}&cpf=${cpf}`, '_blank');
    } else {
      alert('Datas de início e fim, além do CPF, são necessários.');
    }
  };

  const handleToggleExpand = (id) => {
    setExpandedItem(expandedItem === id ? null : id);
  };

  return (
    <div className="text-center">
      <div className="br-list" role="list">
        <div className="header">
          <div className="title">Requisições</div>
        </div>
        <span className="br-divider"></span>
        {requisicoes.map((req) => (
          <React.Fragment key={req.idRequisicao}>
            <div className="br-item" role="listitem" data-toggle="collapse" data-target={`l${req.idRequisicao}`}>
              <div className="content" onClick={() => handleToggleExpand(req.idRequisicao)}>
                <div className="flex-fill">Requisição ID: {req.idRequisicao}</div>
                <i className={`fas ${expandedItem === req.idRequisicao ? 'fa-angle-up' : 'fa-angle-down'}`} aria-hidden="true"></i>
              </div>
            </div>
            <div className={`br-list ${expandedItem === req.idRequisicao ? '' : 'hidden'}`} id={`l${req.idRequisicao}`} role="list" hidden={expandedItem !== req.idRequisicao}>
              <div className="br-item" role="listitem">
                <div className="row align-items-center">
                  <div className="col-auto"><i className="fas fa-heartbeat" aria-hidden="true"></i></div>
                  <div className="col">Descrição: {req.descricaoResultado}</div>
                </div>
              </div>
              <span className="br-divider"></span>
              <div className="br-item" role="listitem">
                <div className="row align-items-center">
                  <div className="col-auto"><i className="fas fa-calendar-alt" aria-hidden="true"></i></div>
                  <div className="col">Data: {req.dataResultado}</div>
                </div>
              </div>
              <span className="br-divider"></span>
              <div className="br-item" role="listitem">
                <div className="row align-items-center">
                  <div className="col-auto"><i className="fas fa-check-circle" aria-hidden="true"></i></div>
                  <div className="col">Aprovado: {req.aprovado ? 'Sim' : 'Não'}</div>
                </div>
              </div>
              <span className="br-divider"></span>
            </div>
          </React.Fragment>
        ))}
      </div>

      {showInput && (
        <div className="col-sm-10 col-lg-7 mb-3 mx-auto">
          <div className="br-input input-inline">
            <div className="input-label">
              <label className="text-nowrap" htmlFor="input-id">Digite o ID da Requisição</label>
            </div>
            <div className="input-content">
              <input
                id="input-id"
                type="text"
                value={inputId}
                onChange={handleInputChange}
                placeholder="Digite o ID da requisição"
                className="form-control"
                maxLength={10} // Ajuste conforme necessário
              />
            </div>
          </div>
          <div className="mt-3">
            <button className="br-button secondary mx-2 mb-2" type="button" onClick={handleGeneratePdf}>
              Gerar PDF
            </button>
            <button className="br-button secondary mx-2 mb-2" type="button" onClick={() => setShowInput(false)}>
              Cancelar
            </button>
          </div>
        </div>
      )}

      <div className="p-3">
        <button className="br-button secondary mx-2 mb-4" type="button" onClick={handleGeneratePdfById}>
          Gerar PDF por ID
        </button>
        <button className="br-button secondary mx-2 mb-4" type="button" onClick={handleGeneratePdfByPeriod}>
          Gerar PDF por Período
        </button>
        <div className="row justify-content-center align-items-center mb-4">
          <div className="col-auto">
            <label htmlFor="inicio">Início:</label>
            <input
              id="inicio"
              type="date"
              value={inicio}
              onChange={(e) => setInicio(e.target.value)}
              className="form-control"
            />
          </div>
          <div className="col-auto">
            <label htmlFor="fim">Fim:</label>
            <input
              id="fim"
              type="date"
              value={fim}
              onChange={(e) => setFim(e.target.value)}
              className="form-control"
            />
          </div>
        </div>
        <button className="br-button secondary mx-2 mb-4" type="button" onClick={handleSearchAnotherCpf}>
          Buscar Outro CPF
        </button>
      </div>
    </div>
  );
};

export default ListarRequisicoes;
