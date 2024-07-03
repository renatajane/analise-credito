package com.analisedecredito.aplicacao_analise_credito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.RendaTipoDto;
import com.analisedecredito.aplicacao_analise_credito.model.RendaTipo;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.service.RendaTipoService;

@RestController
@RequestMapping("/teste")
public class TesteController {
    
    @Autowired
    RendaTipoRepository repository;

    @Autowired
    RendaTipoService service;

    @GetMapping("/aqui")
    public String teste(){
        return "aqui é um teste";
    }

    @GetMapping("")
    public String nada(){
        return "esse aqui é diferente";
    }

    @GetMapping("/renda/{id}")
    public String rendaById(@PathVariable Integer id){
        return repository.findById(id).get().getDescricaoRendaTipo();
    }

    @GetMapping("/lista")
    public List<RendaTipo> listaRenda(@RequestBody RendaTipoDto rendaTipoDto){
        return service.list();
    }
}
