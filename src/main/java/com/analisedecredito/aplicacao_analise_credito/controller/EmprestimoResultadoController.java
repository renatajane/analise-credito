package com.analisedecredito.aplicacao_analise_credito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoReadDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoResultadoService;

@RestController
@RequestMapping("/emprestimo-resultado")
public class EmprestimoResultadoController {

    @Autowired
    EmprestimoResultadoService service;

    @GetMapping("/{id}")
    public EmprestimoResultadoReadDto findById(@PathVariable("id") Integer id){
        return service.findById(id);
    }


    
}
