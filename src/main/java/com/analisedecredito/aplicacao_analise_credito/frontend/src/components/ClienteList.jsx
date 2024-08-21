import React, { useEffect, useState } from "react";
import axios from "axios";

const ClientList = () => {
  const [clients, setClients] = useState([]);

  useEffect(() => {
    // Faz a requisição para obter a lista de clientes
    axios.get("http://localhost:8080/cliente/list")
      .then(response => {
        setClients(response.data);
      })
      .catch(error => {
        console.error("Houve um erro ao buscar a lista de clientes!", error);
      });
  }, []);

  return (
    <div>
      <h1>Lista de Clientes</h1>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>CPF</th>
            {/* Adicione mais colunas conforme necessário */}
          </tr>
        </thead>
        <tbody>
          {clients.map(client => (
            <tr key={client.idCliente}>
              <td>{client.idCliente}</td>
              <td>{client.nome}</td>
              <td>{client.cpf}</td>
              {/* Adicione mais colunas conforme necessário */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ClientList;
