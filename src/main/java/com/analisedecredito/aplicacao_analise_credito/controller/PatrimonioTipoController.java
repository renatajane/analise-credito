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

import com.analisedecredito.aplicacao_analise_credito.dto.PatrimonioTipoDto;
import com.analisedecredito.aplicacao_analise_credito.repository.PatrimonioTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.service.PatrimonioTipoService;

@RestController
@RequestMapping("/patrimonio-tipo")
public class PatrimonioTipoController {

    @Autowired
    PatrimonioTipoRepository repository;

    @Autowired
    PatrimonioTipoService service;

    /* Retorna um tipo de patrimônio de acordo com o id */
    @GetMapping("/{id}")
    private PatrimonioTipoDto findById(@PathVariable("id") Integer id){
        return new PatrimonioTipoDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todos os tipos de patrimonio cadastrados */
    @GetMapping("/list")
    private List<PatrimonioTipoDto> list(){
        return service.list();
    }

    /* Cria um novo tipo de patrimônio com base nos dados fornecidos */
    @PostMapping("/create")
    public void create(@RequestBody PatrimonioTipoDto patrimonioTipoDto){
        if(patrimonioTipoDto.getDescricaoPatrimonioTipo().isEmpty() ||
        patrimonioTipoDto.getDescricaoPatrimonioTipo() == null){
            throw new IllegalArgumentException("É necessário inserir uma descrição.");
        }
        service.create(patrimonioTipoDto);
    }

    /* Atualiza os dados de um tipo de patrimônio existente */
    @PutMapping("/update")
    public void update(@RequestBody PatrimonioTipoDto patrimonioTipoDto){
        service.update(patrimonioTipoDto);
    }

    /* Remove um dado de tipo de renda pelo id */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }  
    
}
