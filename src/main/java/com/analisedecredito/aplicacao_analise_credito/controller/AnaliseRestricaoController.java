package com.analisedecredito.aplicacao_analise_credito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.AnaliseRestricaoDto;
import com.analisedecredito.aplicacao_analise_credito.service.AnaliseRestricaoService;

@RestController
@RequestMapping("/analise-restricao")
public class AnaliseRestricaoController {

    @Autowired
    AnaliseRestricaoService service;

    /* Retorna uma analise de restrição de acordo com o id */
    @GetMapping("/{id}")
    public AnaliseRestricaoDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de análises de restrições cadastrados */
    @GetMapping("/list")
    public List<AnaliseRestricaoDto> list() {
        return service.list();
    }

    /* Cria uma nova análise de restrição com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody AnaliseRestricaoDto analiseRestricaoDto) {
        service.create(analiseRestricaoDto);
    }

    /* Atualiza os dados de uma análise de restrição existente */
    @PutMapping
    public ResponseEntity<AnaliseRestricaoDto> update
    (@RequestBody AnaliseRestricaoDto analiseRestricaoDto) {
        try {
            Integer id = analiseRestricaoDto.getIdRestricao();
            AnaliseRestricaoDto updatedAnalise = service.update(id, analiseRestricaoDto);
            return ResponseEntity.ok(updatedAnalise);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /* Remove uma análise de restrição pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);;
        return ResponseEntity.ok().build();
    }
}
