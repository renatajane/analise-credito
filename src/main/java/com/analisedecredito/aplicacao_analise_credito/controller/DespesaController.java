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

import com.analisedecredito.aplicacao_analise_credito.dto.DespesaDto;
import com.analisedecredito.aplicacao_analise_credito.dto.DespesaReadDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.service.DespesaService;

@RestController
@RequestMapping("/despesa")
public class DespesaController {

    @Autowired
    DespesaService service;

    /* Retorna despesa de acordo com o id */
    @GetMapping("/{id}")
    public DespesaReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de todas as despesas cadastrados */
    @GetMapping("/list")
    public List<DespesaReadDto> list() {
        return service.list();
    }

    /* Cria uma nova despesa com base nos dados fornecidos */
    @PostMapping
    public DespesaDto createDespesa(@RequestBody DespesaDto despesaDto) {
        // Verifica se o despesaDto recebido não é nulo e se o cliente está presente
        // if (despesaDto == null || despesaDto.getCliente() == null) {
        // throw new RuntimeException("Dados de cliente inválidos na requisição.");
        // }

        // Chama o serviço para criar a despesa
        return service.create(despesaDto);
    }

     /* Atualiza os dados de uma despesa existente */
    @PutMapping
    public ResponseEntity<DespesaDto> update(@RequestBody DespesaDto despesaDto) {
        try {
            Integer id = despesaDto.getIdDespesa();
            DespesaDto updatedDespesa = service.update(id, despesaDto);
            return ResponseEntity.ok(updatedDespesa);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /* Remove uma despesa pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
