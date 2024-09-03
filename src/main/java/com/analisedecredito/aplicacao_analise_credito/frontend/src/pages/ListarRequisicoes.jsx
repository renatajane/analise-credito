import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import StylesTable from './Table.module.css';
import FormatDate from "../utils/formatDate"
import FormatValor from "../utils/formatCurrency"
import { ApiService } from '../services/appService';
import { useAuth } from '../contexto/AuthProvider';
import ClienteNaoCadastrado from '../pages/ClienteNaoCadastrado';
import ClienteNaoTemRequisicao from '../pages/ClienteNaoTemRequisicao'

const ListarRequisicoes = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [requisicoes, setRequisicoes] = useState([]);
  const [idRequisicao, setIdRequisicao] = useState('');
  const [inicio, setInicio] = useState('');
  const [fim, setFim] = useState('');
  const [expandedItem, setExpandedItem] = useState(null);
  const [showInput, setShowInput] = useState(false);
  const [showPeriodInputs, setShowPeriodInputs] = useState(false);
  const [inputId, setInputId] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [idCliente, setIdCliente] = useState(null);

  const { cpfLogado } = useAuth();

  useEffect(() => {
    // Fetch ID do Cliente
    const fetchClientId = async () => {
      try {
        const response = await ApiService.Get(`cliente/cpf/${cpfLogado}`);
        if (response && response.data) {
          setIdCliente(response.data.idCliente);
          const responseRequisicoes = await ApiService.Get(`emprestimo-requisicao/list-by-cpf?cpf=${cpfLogado}`);

          const data = Array.isArray(responseRequisicoes.data) ? responseRequisicoes.data : [];
          setRequisicoes(data);
        }
      } catch (error) {
        console.error('Erro ao buscar ID do cliente:', error);
      }
    };

    fetchClientId();
  }, []);

  // useEffect(() => {
  //   if (location.state && location.state.requisicoes) {
  //     console.log('Requisições recebidas:', location.state.requisicoes);
  //     setRequisicoes(location.state.requisicoes);
  //   } else {
  //     navigate('/buscarRequisicao');
  //   }
  // }, [location.state, navigate]);

  const handleGeneratePdfById = () => {
    setShowInput(true);
    setErrorMessage(''); // Limpar mensagem de erro quando abrir o input novamente
  };

  const handleInputChange = (e) => {
    setInputId(e.target.value);
  };

  const handleGeneratePdf = () => {
    const requisicao = requisicoes.find(req => req.idRequisicao === parseInt(inputId, 10));

    if (requisicao && cpfLogado) {
      window.open(`http://localhost:8080/emprestimo-requisicao/pdf/${cpfLogado}/${inputId}`, '_blank');
      setShowInput(false);
      setInputId('');
      setErrorMessage('');
    } else {
      setErrorMessage('Você precisa inserir um número de requisição existente.');
    }
  };

  const handleGeneratePdfByPeriod = () => {
    setShowPeriodInputs(true);
    setErrorMessage('');
  };

  const validateDates = () => {
    const startDate = new Date(inicio);
    const endDate = new Date(fim);
    const today = new Date();

    if (inicio === '' || fim === '') {
      setErrorMessage('Ambas as datas devem ser preenchidas.');
      return false;
    }

    if (isNaN(startDate.getTime()) || isNaN(endDate.getTime())) {
      setErrorMessage('Por favor, insira datas válidas.');
      return false;
    }

    if (startDate > endDate) {
      setErrorMessage('A data de início não pode ser maior que a data de fim.');
      return false;
    }

    if (startDate > today || endDate > today) {
      setErrorMessage('As datas não podem ser maiores que a data atual.');
      return false;
    }

    if (startDate.getFullYear() < 1900 || endDate.getFullYear() < 1900) {
      setErrorMessage('Por favor, insira datas em um período válido.');
      return false;
    }

    return true;
  };

  const handleGeneratePdfByPeriodSubmit = () => {
    if (validateDates()) {
      window.open(`http://localhost:8080/emprestimo-requisicao/pdf/periodo?inicio=${inicio}&fim=${fim}&cpf=${cpfLogado}`, '_blank');
      setShowPeriodInputs(false);
    }
  };

  const handleToggleExpand = (id) => {
    setExpandedItem(expandedItem === id ? null : id);
  };

  return (
    <div>
      {idCliente == null && <ClienteNaoCadastrado />}
      {idCliente != null && requisicoes.length == 0 && <ClienteNaoTemRequisicao />}
      {requisicoes.length > 0 && (
        <>
          <h3 className={StylesTable.color}>
            Gerar PDF de Requisições
          </h3>
          <div className={StylesTable.container}>
            <div className="text-center">
              <div className="br-list" role="list">
                <div className="header">
                  <div className="title">Lista de Requisições</div>
                </div>
                <span className="br-divider"></span>
                {requisicoes.map((req) => (
                  <React.Fragment key={req.idRequisicao}>
                    <div className="br-item" role="listitem" data-toggle="collapse" data-target={`l${req.idRequisicao}`}>
                      <div className="content" onClick={() => handleToggleExpand(req.idRequisicao)}>
                        <div className="flex-fill">Código da Requisição: {req.idRequisicao}</div>
                        <i className={`fas ${expandedItem === req.idRequisicao ? 'fa-angle-up' : 'fa-angle-down'}`} aria-hidden="true"></i>
                      </div>
                    </div>
                    <div className={`br-list ${expandedItem === req.idRequisicao ? '' : 'hidden'}`} id={`l${req.idRequisicao}`} role="list" hidden={expandedItem !== req.idRequisicao}>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-check-circle" aria-hidden="true"></i></div>
                          <div className="col">Situação: {req.aprovado ? 'Aprovado' : 'Negado'}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-heartbeat" aria-hidden="true"></i></div>
                          <div className="col">Descrição da Situação: {req.descricaoResultado}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-calendar-alt" aria-hidden="true"></i></div>
                          <div className="col">Data da Requisição: {FormatDate(req.dataRequisicao)}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-calendar-check" aria-hidden="true"></i></div>
                          <div className="col">Data do Resultado: {FormatDate(req.dataResultado)}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-user" aria-hidden="true"></i></div>
                          <div className="col">Cliente: {req.cliente.nome}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-calendar" aria-hidden="true"></i></div>
                          <div className="col">Modalidade do Empréstimo: {req.emprestimoModalidade.descricaoModalidade}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-bullseye" aria-hidden="true"></i></div>
                          <div className="col">Objetivo: {req.emprestimoObjetivo.descricaoObjetivo}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-clock" aria-hidden="true"></i></div>
                          <div className="col">Urgência: {req.emprestimoUrgencia.prazoUrgencia}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-dollar-sign" aria-hidden="true"></i></div>
                          <div className="col">Valor Requerido: {FormatValor(req.valorRequerido)}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-dollar-sign" aria-hidden="true"></i></div>
                          <div className="col">Valor Total com Juros: {FormatValor(req.valorTotal)}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-calculator" aria-hidden="true"></i></div>
                          <div className="col">Valor da Parcela: {FormatValor(req.valorParcela)}</div>
                        </div>
                      </div>
                      <span className="br-divider"></span>
                      <div className="br-item" role="listitem">
                        <div className="row align-items-center">
                          <div className="col-auto"><i className="fas fa-calendar-day" aria-hidden="true"></i></div>
                          <div className="col">Prazo (Meses): {req.prazoMes}</div>
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
                      <label className="text-nowrap" htmlFor="input-id">Digite o código da Requisição</label>
                    </div>
                    <div className="input-content">
                      <input
                        id="input-id"
                        type="text"
                        value={inputId}
                        onChange={handleInputChange}
                        placeholder="Digite o código da requisição que deseja gerar o PDF"
                        className="form-control rounded-input"
                        maxLength={10}
                      />
                    </div>
                  </div>
                  {errorMessage && (
                    <div className="br-message danger mt-2">
                      <div className="icon"><i className="fas fa-times-circle fa-lg" aria-hidden="true"></i></div>
                      <div className="content" role="alert">
                        <span className="message-title">Erro:</span>
                        <span className="message-body">{errorMessage}</span>
                      </div>
                    </div>
                  )}
                  <div className="mt-3 d-flex justify-content-center">
                    <button
                      className="br-button secondary btn-blue"
                      type="button"
                      onClick={handleGeneratePdf}
                      style={{ marginRight: '10px' }}
                    >
                      Gerar PDF
                    </button>
                    <button
                      className="br-button secondary"
                      type="button"
                      onClick={() => setShowInput(false)}
                    >
                      Cancelar
                    </button>
                  </div>
                </div>
              )}

              <div className="p-3">
                <div className="d-flex justify-content-center mb-4">
                  <button
                    className="br-button primary btn-white"
                    type="button"
                    onClick={handleGeneratePdfById}
                    style={{ marginRight: '10px' }}
                  >
                    Gerar PDF por Requisição
                  </button>
                  <button
                    className="br-button primary"
                    type="button"
                    onClick={handleGeneratePdfByPeriod}
                  >
                    Gerar PDF por Período
                  </button>
                </div>

                {showPeriodInputs && (
                  <div className="col-sm-10 col-lg-7 mb-3 mx-auto">
                    <div className="br-input input-inline">
                      <div className="input-label">
                        <label className="text-nowrap" htmlFor="inicio">Data de Início</label>
                      </div>
                      <div className="input-content">
                        <input
                          id="inicio"
                          type="date"
                          value={inicio}
                          onChange={(e) => setInicio(e.target.value)}
                          className="form-control rounded-input"
                        />
                      </div>
                    </div>
                    <div className="br-input input-inline mt-3">
                      <div className="input-label">
                        <label className="text-nowrap" htmlFor="fim">Data de Fim</label>
                      </div>
                      <div className="input-content">
                        <input
                          id="fim"
                          type="date"
                          value={fim}
                          onChange={(e) => setFim(e.target.value)}
                          className="form-control rounded-input"
                        />
                      </div>
                    </div>
                    {errorMessage && (
                      <div className="br-message danger mt-2">
                        <div className="icon"><i className="fas fa-times-circle fa-lg" aria-hidden="true"></i></div>
                        <div className="content" role="alert">
                          <span className="message-title">Erro:</span>
                          <span className="message-body">{errorMessage}</span>
                        </div>
                      </div>
                    )}
                    <div className="mt-3 d-flex justify-content-center">
                      <button
                        className="br-button primary"
                        type="button"
                        onClick={handleGeneratePdfByPeriodSubmit}
                        style={{ marginRight: '10px' }}
                      >
                        Gerar PDF
                      </button>
                      <button
                        className="br-button secondary"
                        type="button"
                        onClick={() => setShowPeriodInputs(false)}
                      >
                        Cancelar
                      </button>
                    </div>
                  </div>
                )}

                <div className="d-flex justify-content-center">
                  <button
                    className="br-button secondary"
                    type="button"
                    onClick={() => navigate("/")}
                  >
                    Voltar
                  </button>
                </div>
              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default ListarRequisicoes;
