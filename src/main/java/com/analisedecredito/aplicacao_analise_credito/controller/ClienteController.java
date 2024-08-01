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

import com.analisedecredito.aplicacao_analise_credito.dto.ClienteDto;
import com.analisedecredito.aplicacao_analise_credito.dto.ClienteReadDto;
import com.analisedecredito.aplicacao_analise_credito.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteService service;

    /* Retorna um cliente de acordo com o id */
    @GetMapping("/{id}")
    public ClienteReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de todos os clientes cadastrados */
    @GetMapping("/list")
    public List<ClienteReadDto> list() {
        return service.list();
    }

    /* Cria um novo cliente com base nos dados fornecidos */
    
    @PostMapping
    public void create(@RequestBody ClienteDto clienteDto) {
        service.create(clienteDto);
    }

    /* Atualiza os dados de um cliente existente */
    @PutMapping
    public ResponseEntity<ClienteDto> update(@RequestBody ClienteDto clienteDto) {
        try {
            Integer id = clienteDto.getIdCliente();
            ClienteDto updatedCliente = service.update(id, clienteDto);
            return ResponseEntity.ok(updatedCliente);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /* Soma a renda total do cliente por id */
    @GetMapping("/renda/{id}")
    public Double getRendaCliente(@PathVariable("id") Integer id) {
        return service.somaRenda(id);
    }

    /* Soma o patrimônio total do cliente por id */
    @GetMapping("/patrimonio/{id}")
    public Double getPatrimonioCliente(@PathVariable("id") Integer id){
        return service.somaPatrimonio(id);
    }

    @GetMapping("/score/{id}")
    public Integer getScoreCliente(@PathVariable("id") Integer id){
        return service.calculaScore(id);
    }

    /* Remove um cliente pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
