import React, { useState, useEffect } from 'react';
import axios from 'axios';

const CadastroDespesa = ({id}) => {

    const initialData = {
        idDespesa: 0 ,
    };

    const [idDespesa, setIdDespesa] = useState(initialData.idDespesa);
    const [errors, setErrors] = useState({
        idDespesa: 0,
    });

    if(id!= null){
        useEffect(() => {
            const fetchData = async () => {
                try {
                    const response = await axios.get(`http://localhost:8080/despesa/idCliente/${id}`)
                    //     {
                    //     // params: { cpf: '77239658007' } // Substitua pelo CPF que você deseja buscar
                    // });
                    setIdDespesa(response.data.idDespesa);
                    console.log("MEU RESPONSE DE DESPESAAAAA **** ", response.data);
    
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
                    console.error('Erro ao buscar os dados de DESPESA:', error);
                    // alert('Erro ao buscar os dados do cliente.');
                }
            };
    
            fetchData();
        }, []); // O array vazio garante que a requisição seja feita apenas uma vez, quando o componente é montado.
    
    }
     return (
        <div> OLÁ DESPESA!</div>
     );
};

export default CadastroDespesa;