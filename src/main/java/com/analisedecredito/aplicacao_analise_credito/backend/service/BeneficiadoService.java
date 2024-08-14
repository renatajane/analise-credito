package com.analisedecredito.aplicacao_analise_credito.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.BeneficiadoDto;

@Service
public class BeneficiadoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${wiremock.url}")
    private String wiremockBaseUrl;

    public BeneficiadoService() {
    }

    public BeneficiadoDto buscaBeneficiado(String cpf) throws RestClientException {
        String url = wiremockBaseUrl + "/beneficiado/" + cpf;

        try {
            return restTemplate.getForObject(url, BeneficiadoDto.class);
        } catch (Exception e) {
            // Log o erro se necessário
            System.err.println("Nenhum dado encontrado para o CPF: " + cpf);
            // Retorna null ou um objeto padrão, conforme sua necessidade
            return null; 
        }
    }

}
