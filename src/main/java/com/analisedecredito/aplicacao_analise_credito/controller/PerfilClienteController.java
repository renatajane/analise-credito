package com.analisedecredito.aplicacao_analise_credito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.PerfilClienteDto;
import com.analisedecredito.aplicacao_analise_credito.service.PerfilClienteService;

@RestController
@RequestMapping("/perfil-cliente")
public class PerfilClienteController {

    @Autowired
    PerfilClienteService service;

    /* Retorna um perfil de crédito de acordo com o id */
    @GetMapping("/{id}")
    public PerfilClienteDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de perfis de créditos cadastrados */
    @GetMapping("/list")
    public List<PerfilClienteDto> list() {
        return service.list();
    }

    /* Cria um novo perfil de crédito com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody PerfilClienteDto perfilClienteDto) {
        service.create(perfilClienteDto);
    }

    /*
     * Atualiza os dados de um perfil de crédito existente com base nos dados
     * fornecidos
     */
    @PutMapping
    public PerfilClienteDto update(@RequestBody PerfilClienteDto perfilClienteDto) {
        return service.update(perfilClienteDto);
    }

    /* Remove um tipo de perfil de crédito de acordo com o id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }


    
}
