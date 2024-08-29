import React, { useState, useEffect } from 'react';
import axios from 'axios';

const CadastroPatrimonio = ({id}) => {

    if(id!= null){
        useEffect(() => {
            const fetchData = async () => {
                try {
                    const response = await axios.get(`http://localhost:8080/patrimonio/${id}`)
                    //     {
                    //     // params: { cpf: '77239658007' } // Substitua pelo CPF que você deseja buscar
                    // });
    
                    // Preencha os estados com os dados recebidos
                    // setIdCliente(response.data.idCliente);
                    // setNome(response.data.nome);
                    // setCpf(response.data.cpf);
                    // setDataNascimento(response.data.dataNascimento);
                    // setEmail(response.data.email);
                    // setTelefone(response.data.telefone);
                    // setEndereco(response.data.endereco);
                    // setAutorizacaoLGPD(response.data.autorizacaoLGPD);
                    // setSpcSerasa(response.data.spcSerasa);
    
                    // alert('Dados carregados com sucesso!');
                } catch (error) {
                    console.error('Erro ao buscar os dados do cliente:', error);
                    // alert('Erro ao buscar os dados do cliente.');
                }
            };
    
            fetchData();
        }, []); // O array vazio garante que a requisição seja feita apenas uma vez, quando o componente é montado.
    
    }
     return (
        <div> OLÁ PATRIMONIO !</div>
     );
};

export default CadastroPatrimonio;