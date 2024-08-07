package com.analisedecredito.aplicacao_analise_credito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.BeneficiadoDto;
import com.analisedecredito.aplicacao_analise_credito.service.BeneficiadoService;

@RestController
@RequestMapping("/beneficiado")
public class BeneficiadoController {

    @Autowired
    private BeneficiadoService beneficiadoService;

    @GetMapping("/beneficiado/{cpf}")
    public ResponseEntity<BeneficiadoDto> getBeneficiado(@PathVariable String cpf) {
        try {
            BeneficiadoDto beneficiado = beneficiadoService.buscaBeneficiado(cpf);
            return ResponseEntity.ok(beneficiado);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null); 
        }
    }
}