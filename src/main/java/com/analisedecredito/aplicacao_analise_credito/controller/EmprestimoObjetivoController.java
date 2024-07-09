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

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoObjetivoDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoObjetivoService;



@RestController
@RequestMapping("/emprestimo-objetivo")
public class EmprestimoObjetivoController {
    
    @Autowired
    EmprestimoObjetivoService service;

    /* Retorna um objetivo de empréstimo de acordo com o id */
    @GetMapping("/{id}")
    public EmprestimoObjetivoDto findById(@PathVariable("id") Integer id){
        return service.findById(id);
    }

    /* Retorna uma lista de todos os tipos de objetivo de emprestimo cadastrados */
    @GetMapping("/list")
    public List<EmprestimoObjetivoDto> list(){
        return service.list();
    }

    /* Cria um novo objetivo de empréstimo com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody EmprestimoObjetivoDto emprestimoObjetivoDto){
        if(emprestimoObjetivoDto.getDescricaoObjetivo() == null ||
        emprestimoObjetivoDto.getDescricaoObjetivo().isEmpty()){
            throw new IllegalArgumentException("É necessário inserir uma descrição.");
        }
        service.create(emprestimoObjetivoDto);
    }
    
    /* Atualiza os dados de um objetivo de empréstimo existente */
    @PutMapping
    public EmprestimoObjetivoDto update(@RequestBody EmprestimoObjetivoDto emprestimoObjetivoDto){
        return service.update(emprestimoObjetivoDto);
    }

    /* Remove um dado de objetivo de empréstimo pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
