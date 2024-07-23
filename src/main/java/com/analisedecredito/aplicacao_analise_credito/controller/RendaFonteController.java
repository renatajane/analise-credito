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

import com.analisedecredito.aplicacao_analise_credito.dto.RendaFonteDto;
import com.analisedecredito.aplicacao_analise_credito.dto.RendaFonteReadDto;
import com.analisedecredito.aplicacao_analise_credito.service.RendaFonteService;

@RestController
@RequestMapping("/renda-fonte")
public class RendaFonteController {

    @Autowired
    RendaFonteService service;

    /* Retorna uma fonte de renda de acordo com o id */
    @GetMapping("/{id}")
    public RendaFonteReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de todas as fontes de renda cadastradas */
    @GetMapping
    public List<RendaFonteReadDto> list() {
        return service.list();
    }

    /* Cria uma nova fonte de renda com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody RendaFonteDto rendaFonteDto) {
        service.create(rendaFonteDto);
    }

    /* Atualiza os dados de uma fonte de renda existente */
    @PutMapping
    public ResponseEntity<RendaFonteDto> update(@RequestBody RendaFonteDto rendaFonteDto) {
        Integer id = rendaFonteDto.getIdRendaFonte();
        RendaFonteDto updatedFonteRenda = service.update(id, rendaFonteDto);
        return ResponseEntity.ok(updatedFonteRenda);
    }

    /* Remove um dado de fonte de renda pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    
    }

}
