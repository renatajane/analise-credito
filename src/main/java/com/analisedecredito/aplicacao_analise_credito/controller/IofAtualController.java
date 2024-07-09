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

import com.analisedecredito.aplicacao_analise_credito.dto.IofAtualDto;
import com.analisedecredito.aplicacao_analise_credito.service.IofAtualService;

@RestController
@RequestMapping("/iof-atual")
public class IofAtualController {

    @Autowired
    IofAtualService service;

    /* Retorna iof de acordo com o id */
    @GetMapping("/{id}")
    public IofAtualDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de iofs cadastrados */
    @GetMapping("/list")
    public List<IofAtualDto> list() {
        return service.list();
    }

    /* Cria um novo iof com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody IofAtualDto iofAtualDto) {
        service.create(iofAtualDto);
    }

    /* Atualiza os dados de iof existente */
    @PutMapping
    public IofAtualDto update(@RequestBody IofAtualDto iofAtualDto) {
        return service.update(iofAtualDto);
    }

    /* Remove um dado de iof pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
