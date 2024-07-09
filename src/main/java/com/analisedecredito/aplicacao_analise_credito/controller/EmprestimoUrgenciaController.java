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

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoUrgenciaDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoUrgenciaService;

@RestController
@RequestMapping("/emprestimo-urgencia")
public class EmprestimoUrgenciaController {

    @Autowired
    EmprestimoUrgenciaService service;

    /* Retorna uma urgência de empréstimo de acordo com o id */
    @GetMapping("/{id}")
    public EmprestimoUrgenciaDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de todos os tipos de urgência de empréstimo cadastrados */
    @GetMapping("/list")
    public List<EmprestimoUrgenciaDto> list() {
        return service.list();
    }

    /* Cria um novo urgência empréstimo com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody EmprestimoUrgenciaDto emprestimoUrgenciaDto) {
        if (emprestimoUrgenciaDto.getPrazoUrgencia() == null ||
                emprestimoUrgenciaDto.getPrazoUrgencia().isEmpty()) {
            throw new IllegalArgumentException("É necessário inserir um prazo.");
        }
        service.create(emprestimoUrgenciaDto);
    }

    /* Atualiza os dados de urgência empréstimo existente */
    @PutMapping
    public EmprestimoUrgenciaDto update(@RequestBody EmprestimoUrgenciaDto emprestimoUrgenciaDto) {
        return service.update(emprestimoUrgenciaDto);
    }

    /* Remove um dado de objetivo de empréstimo pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
