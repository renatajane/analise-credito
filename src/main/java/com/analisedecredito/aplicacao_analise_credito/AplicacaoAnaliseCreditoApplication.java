package com.analisedecredito.aplicacao_analise_credito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.analisedecredito.aplicacao_analise_credito.config")
public class AplicacaoAnaliseCreditoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AplicacaoAnaliseCreditoApplication.class, args);
		
	}
}
