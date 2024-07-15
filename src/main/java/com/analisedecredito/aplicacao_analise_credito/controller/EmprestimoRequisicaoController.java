package com.analisedecredito.aplicacao_analise_credito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoReadDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoRequisicaoService;

@RestController
@RequestMapping("/emprestimo-requisicao")
public class EmprestimoRequisicaoController {
    
    @Autowired
    EmprestimoRequisicaoService service;

    /* Retorna uma requisição de empréstimo de acordo com o id */
    @GetMapping("/{id}")
    public EmprestimoRequisicaoReadDto findById(@PathVariable("id") Integer id){
        return service.findById(id);
    }

    /* Retorna uma lista requisições de empréstimos cadastrados */
    @GetMapping("/list")
    public List<EmprestimoRequisicaoReadDto> list(){
        return service.list();
    }

    /* Cria uma nova requisição de empréstimo com base nos dados fornecidos */
    
}
