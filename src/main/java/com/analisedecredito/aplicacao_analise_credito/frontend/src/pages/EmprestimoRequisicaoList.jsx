import React from 'react';
import Relatorios from './Relatorios'; 
import ActionButtons from './ActionButtons';

const EmprestimoRequisicaoList = () => {
  return (
    <div className="row">
      <div className="col-sm-12 mb-3 d-flex justify-content-end">
        <Relatorios
          id="input-cpf"
          placeholder="Digite somente números"
          feedbackMessage="Mensagem de Sucesso"
          feedbackType="success"
          label="Nome de CPF:"
        />
      </div>
      <ActionButtons />
    </div>
  );
};

export default EmprestimoRequisicaoList;
