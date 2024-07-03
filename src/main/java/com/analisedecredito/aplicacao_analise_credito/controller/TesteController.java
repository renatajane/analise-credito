package com.analisedecredito.aplicacao_analise_credito.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {
    
    @GetMapping("/aqui")
    public String teste(){
        return "aqui é um teste";
    }

    @GetMapping("")
    public String nada(){
        return "esse aqui é diferente";
    }
}
