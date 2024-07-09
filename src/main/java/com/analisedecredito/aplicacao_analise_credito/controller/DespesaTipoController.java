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

import com.analisedecredito.aplicacao_analise_credito.dto.DespesaTipoDto;
import com.analisedecredito.aplicacao_analise_credito.repository.DespesaTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.service.DespesaTipoService;

@RestController
@RequestMapping("/despesa-tipo")
public class DespesaTipoController {

    @Autowired
    DespesaTipoRepository repository;

    @Autowired
    DespesaTipoService service;

    /* Retorna um tipo de despesa de acordo com o id */
    @GetMapping("/{id}")
    public DespesaTipoDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de todos os tipos de despesas cadastrados */
    @GetMapping("/list")
    public List<DespesaTipoDto> list() {
        return service.list();
    }

    /* Cria um novo tipo de despesa com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody DespesaTipoDto despesaTipoDto) {
        if (despesaTipoDto.getDescricaoDespesaTipo() == null ||
                despesaTipoDto.getDescricaoDespesaTipo().isEmpty()) {
            throw new IllegalArgumentException("É necessário inserir uma descrição.");
        }
        service.create(despesaTipoDto);
    }

    /* Atualiza os dados de um tipo de despesa existente */
    @PutMapping
    private void update (@RequestBody DespesaTipoDto despesaTipoDto){
        service.update(despesaTipoDto);
    }

    /* Remove um dado de tipo de despesa pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
