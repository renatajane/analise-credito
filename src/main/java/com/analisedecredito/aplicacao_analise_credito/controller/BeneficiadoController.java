package com.analisedecredito.aplicacao_analise_credito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.service.BeneficiadoService;

@RestController
@RequestMapping("/beneficiado")
public class BeneficiadoController {

    @Autowired
    private BeneficiadoService beneficiadoService;

    @GetMapping("/{cpf}")
    public String getBeneficiado(@PathVariable String cpf) {
        return beneficiadoService.getBeneficiadoInfo(cpf);
    }
}