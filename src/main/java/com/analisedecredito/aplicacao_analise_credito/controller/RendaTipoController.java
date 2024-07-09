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

import com.analisedecredito.aplicacao_analise_credito.dto.RendaTipoDto;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.service.RendaTipoService;

@RestController
@RequestMapping("/renda-tipo")
public class RendaTipoController {

    @Autowired
    RendaTipoRepository repository;

    @Autowired
    RendaTipoService service;

    /* Retorna um tipo de renda de acordo com o id */
    @GetMapping("/{id}")
    public RendaTipoDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de todos os tipos de renda cadastrados */
    @GetMapping("/list")
    public List<RendaTipoDto> list() {
        return service.list();
    }

    /* Cria um novo tipo de renda com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody RendaTipoDto rendaTipoDto) {
        if (rendaTipoDto.getDescricaoRendaTipo() == null) {
            throw new IllegalArgumentException("Descrição da renda tipo não pode ser nula ou vazia");
        }
        service.create(rendaTipoDto);
    }

    /* Atualiza os dados de um tipo de renda existente */
    @PutMapping
    public RendaTipoDto update(@RequestBody RendaTipoDto rendaTipoDto) {
        return service.update(rendaTipoDto);
    }

    /* Remove um dado de tipo de renda pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
