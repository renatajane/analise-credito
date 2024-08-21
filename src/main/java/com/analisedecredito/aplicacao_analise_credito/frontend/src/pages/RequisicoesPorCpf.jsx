import axios from 'axios';

// Função para buscar requisições de empréstimos com base no CPF
const fetchRequisicoesByCpf = async (cpf) => {
    try {
        const response = await axios.get(`http://localhost:8080/emprestimo-requisicao/list?cpf=${cpf}`);
        return response.data;
    } catch (error) {
        console.error("Erro ao buscar requisições:", error);
        throw error;
    }
};
