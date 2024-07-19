package com.analisedecredito.aplicacao_analise_credito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.PatrimonioDto;
import com.analisedecredito.aplicacao_analise_credito.dto.PatrimonioReadDto;
import com.analisedecredito.aplicacao_analise_credito.service.PatrimonioService;

@RestController
@RequestMapping("/patrimonio")
public class PatrimonioController {

    @Autowired
    PatrimonioService service;

    /* Retorna um patrimônio de acordo com o id */
    @GetMapping("/{id}")
    public PatrimonioReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna um patrimônio cadastrado */
    @GetMapping("/list")
    public List<PatrimonioReadDto> list() {
        return service.list();
    }

    /* Cria um novo patrimônio com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody PatrimonioDto patrimonioDto){
        service.create(patrimonioDto);
    }
}
