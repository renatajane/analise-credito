package com.analisedecredito.aplicacao_analise_credito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoReadDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoRequisicaoService;

@RestController
@RequestMapping("/emprestimo-requisicao")
public class EmprestimoRequisicaoController {

    @Autowired
    EmprestimoRequisicaoService service;

    /* Retorna uma requisição de empréstimo de acordo com o id */
    @GetMapping("/{id}")
    public EmprestimoRequisicaoReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista requisições de empréstimos cadastrados */
    @GetMapping("/list")
    public List<EmprestimoRequisicaoReadDto> list() {
        return service.list();
    }

    /* Cria uma nova requisição de empréstimo com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody EmprestimoRequisicaoDto emprestimoRequisicaoDto) {
        service.create(emprestimoRequisicaoDto);
    }

    /* Atualiza os dados de uma requisição de empréstimo existente */
    @PutMapping()
    public ResponseEntity<EmprestimoRequisicaoDto> updateEmprestimo(
            @RequestBody EmprestimoRequisicaoDto emprestimoRequisicaoDto) {

        try {
            Integer id = emprestimoRequisicaoDto.getIdRequisicao();
            EmprestimoRequisicaoDto updatedEmprestimo = service.update(id, emprestimoRequisicaoDto);
            return ResponseEntity.ok(updatedEmprestimo);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* Remove uma requisição de empréstimo pelo id */  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
