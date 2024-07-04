package com.analisedecredito.aplicacao_analise_credito.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Aplicação de Análise de Crédito API")
                        .version("1.0")
                        .description("Documentação da API da Aplicação de Análise de Crédito"));
    }
}