import React, { useState } from 'react';
import { cpfMask } from '../components/Mask/CpfMask';
import { useNavigate } from 'react-router-dom';
import styles from "./Home.module.css";
import StylesCpf from './Cpf.module.css';
import StylesTable from './Table.module.css';
import { ApiService } from '../services/appService';
import { useAuth } from '../contexto/AuthProvider';

const BuscarRequisicao = () => {
  const [cpf, setCpf] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const { cpfLogado } = useAuth();

  const handleSearch = () => {
    const cpfNumerico = cpf.replace(/\D/g, '');
    if (cpfNumerico.length === 11) {
      setLoading(true);
      setError(null);

      const response = ApiService.Get(`emprestimo-requisicao/list-by-cpf?cpf=${cpfLogado}`);

          const data = Array.isArray(response.data) ? response.data : [];
          if (data.length > 0) {
            navigate('/listarRequisicoes', { state: { cpf: cpfNumerico, requisicoes: data } });
          } 
          else {
            setError('Nenhuma requisição encontrada para o CPF informado.');
          }        
    } else {
      setError('CPF inválido. Verifique o formato e tente novamente.');
    }
  };

  const handleCpfChange = (e) => {
    setCpf(cpfMask(e.target.value));
    setError(null);
  };

  const handleCloseError = () => {
    setError(null);
  };

  return (
    <div>
      <div className={StylesTable.services}>
        <h3 className={StylesTable.color}>
          Consultar Requições para Gerar Relatório
        </h3>
      </div>

      <div className="d-flex justify-content-center align-items-center vh-100">
        <div className={`container text-center ${StylesCpf.container}`}>
          <div className="col-sm-10 col-lg-7 mb-3 mx-auto mt-5">
            <div className="br-input input-inline">
              <div className="input-label">
                <label className="text-nowrap" htmlFor="cpf">CPF</label>
              </div>
              <div className="input-content">
                <input
                  id="cpf"
                  type="text"
                  placeholder="Digite o CPF"
                  value={cpf}
                  onChange={handleCpfChange}
                  maxLength={14}
                  className="form-control"
                />
              </div>
            </div>
            <button className="br-button secondary mb-4 mt-3" type="button" onClick={handleSearch}>
              Buscar Requisições
            </button>
          </div>

          {loading && <p className="mt-4">Carregando...</p>}

          {error && (
            <div className="br-message danger mt-4">
              <div className="icon">
                <i className="fas fa-times-circle fa-lg" aria-hidden="true"></i>
              </div>
              <div className="content" role="alert">
                <span className="message-title">Erro:</span>
                <span className="message-body"> {error}</span>
              </div>
              <div className="close">
                <button className="br-button circle small" type="button" aria-label="Fechar a mensagem de alerta" onClick={handleCloseError}>
                  <i className="fas fa-times" aria-hidden="true"></i>
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default BuscarRequisicao;
