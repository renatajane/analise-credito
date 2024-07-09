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

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoModalidadeDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoModalidadeService;



@RestController
@RequestMapping("/emprestimo-modalidade")
public class EmprestimoModalidadeController {

    @Autowired
    EmprestimoModalidadeService service;

    /* Retorna uma modalidade de empréstimo de acordo com o id */
    @GetMapping("/{id}")
    private EmprestimoModalidadeDto findById(@PathVariable("id")Integer id){
        return service.findById(id);
    }

    /* Retorna uma lista de todas as modalidades de empréstimos cadastrados */
    @GetMapping("/list")
    private List<EmprestimoModalidadeDto> list(){
        return service.list();
    }

    /* Cria um novo tipo de modalidade de empréstimo com base nos dados fornecidos */
    @PostMapping
    private void create(@RequestBody EmprestimoModalidadeDto emprestimoModalidadeDto){
        if(emprestimoModalidadeDto.getDescricaoModalidade() == null ||
        emprestimoModalidadeDto.getDescricaoModalidade().isEmpty()){
            throw new IllegalArgumentException("É preciso inserir uma descrição.");
        }
        service.create(emprestimoModalidadeDto);
    }

    /* Atualiza os dados de modalidade de empréstimo existente com base nos dados fornecidos */
    @PutMapping
    private EmprestimoModalidadeDto update(@RequestBody EmprestimoModalidadeDto emprestimoModalidadeDto){
        return service.update(emprestimoModalidadeDto);
    }

    /* Remove um tipo de modalidade de empréstimo de acordo com o id */
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
