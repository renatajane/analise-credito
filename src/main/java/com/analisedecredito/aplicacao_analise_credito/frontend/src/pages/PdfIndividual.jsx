import React, { useEffect, useState } from "react";
import axios from "axios";

// Função para gerar e baixar o PDF
const PdfIndividual = async (cpf, id) => {
    try {
        const response = await axios.get(`http://localhost:8080/emprestimo-requisicao/pdf/${cpf}/${id}`, {
            responseType: 'blob' // Importante para receber o arquivo PDF como blob
        });

        // Cria um URL para o blob
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'documento.pdf'); // Nome do arquivo PDF
        document.body.appendChild(link);
        link.click();
        link.remove();
    } catch (error) {
        console.error("Erro ao gerar PDF:", error);
    }
};

export default PdfIndividual;