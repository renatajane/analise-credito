import React from 'react';

const EmprestimoRequisicaoList = ({ requisicoes, onPrint }) => {
  if (requisicoes.length === 0) {
    return <p>Nenhuma requisição encontrada.</p>;
  }

  if (requisicoes.length === 1) {
    // Se houver apenas uma requisição, mostre-a diretamente
    const req = requisicoes[0];
    return (
      <div>
        <h3>Requisição Única</h3>
        <p>{req.descricao}</p>
        <button onClick={() => onPrint(req.idRequisicao)}>Imprimir</button>
      </div>
    );
  }

  // Se houver várias requisições, mostre a lista
  return (
    <div>
      <h3>Lista de Requisições</h3>
      <ul>
        {requisicoes.map(req => (
          <li key={req.idRequisicao}>
            <span>{req.descricao} </span>
            <button onClick={() => onPrint(req.idRequisicao)}>Imprimir</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EmprestimoRequisicaoList;
