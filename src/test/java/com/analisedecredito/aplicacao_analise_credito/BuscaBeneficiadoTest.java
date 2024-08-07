package com.analisedecredito.aplicacao_analise_credito;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.analisedecredito.aplicacao_analise_credito.dto.BeneficiadoDto;
import com.analisedecredito.aplicacao_analise_credito.service.BeneficiadoService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Use um perfil específico para testes
public class BuscaBeneficiadoTest {

    @Autowired
    private BeneficiadoService beneficiadoService;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8082));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8082);
    }

    @Test
    public void testBuscaBeneficiado() {
        // Configurar o WireMock para retornar uma resposta mockada
        wireMockServer.stubFor(get(urlEqualTo("/beneficiado/84688392052"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("{ \"cpf\": \"84688392052\", \"valorBeneficio\": 750.00 }")));

        // Chamar o serviço e verificar a resposta
        BeneficiadoDto beneficiado = beneficiadoService.buscaBeneficiado("84688392052");
        assertNotNull(beneficiado);
        assertEquals("84688392052", beneficiado.getCpf());
        assertEquals(750.00, beneficiado.getValorBeneficio(), 0.01);
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }
}