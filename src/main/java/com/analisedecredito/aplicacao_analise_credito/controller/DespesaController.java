package com.analisedecredito.aplicacao_analise_credito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.DespesaDto;
import com.analisedecredito.aplicacao_analise_credito.service.DespesaService;

@RestController
@RequestMapping("/despesa")
public class DespesaController {

    @Autowired
    DespesaService service;

    /* Retorna despesa de acordo com o id */
    @GetMapping("/{id}")
    public DespesaDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de todas as despesas cadastrados */
    @GetMapping("/list")
    public List<DespesaDto> list() {
        return service.list();
    }

    /* Cria uma nova despesa com base nos dados fornecidos */
    @PostMapping
    public DespesaDto createDespesa(@RequestBody DespesaDto despesaDto) {
                // Verifica se o despesaDto recebido não é nulo e se o cliente está presente
                // if (despesaDto == null || despesaDto.getCliente() == null) {
                //     throw new RuntimeException("Dados de cliente inválidos na requisição.");
                // }
                
                // Chama o serviço para criar a despesa
                return service.create(despesaDto);
    }
}
