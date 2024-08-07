package com.analisedecredito.aplicacao_analise_credito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.analisedecredito.aplicacao_analise_credito.dto.BeneficiadoDto;

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
        } catch (RestClientException e) {
            throw new RestClientException("Nenhum dado encontrado para o CPF: " + cpf, e);
        }

    }

}
