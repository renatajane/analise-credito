package com.analisedecredito.aplicacao_analise_credito.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BeneficiadoService {

    private final RestTemplate restTemplate;
    private final String wiremockBaseUrl;

    public BeneficiadoService(RestTemplate restTemplate, @Value("${wiremock.url}") String wiremockBaseUrl) {
        this.restTemplate = restTemplate;
        this.wiremockBaseUrl = wiremockBaseUrl;
    }

    public String getBeneficiadoInfo(String cpf) {
        String url = wiremockBaseUrl + "/beneficiado/" + cpf;
        return restTemplate.getForObject(url, String.class);
    }
}
