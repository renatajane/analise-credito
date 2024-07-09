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

import com.analisedecredito.aplicacao_analise_credito.dto.PerfilCreditoDto;
import com.analisedecredito.aplicacao_analise_credito.service.PerfilCreditoService;

@RestController
@RequestMapping("/perfil-credito")
public class PerfilCreditoController {

    @Autowired
    PerfilCreditoService service;

    /* Retorna um perfil de crédito de acordo com o id */
    @GetMapping("/{id}")
    public PerfilCreditoDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de perfis de créditos cadastrados */
    @GetMapping("/list")
    public List<PerfilCreditoDto> list() {
        return service.list();
    }

    /* Cria um novo perfil de crédito com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody PerfilCreditoDto perfilCreditoDto) {
        service.create(perfilCreditoDto);
    }

    /*
     * Atualiza os dados de um perfil de crédito existente com base nos dados
     * fornecidos
     */
    @PutMapping
    public PerfilCreditoDto update(@RequestBody PerfilCreditoDto perfilCreditoDto) {
        return service.update(perfilCreditoDto);
    }

    /* Remove um tipo de perfil de crédito de acordo com o id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }


    
}
