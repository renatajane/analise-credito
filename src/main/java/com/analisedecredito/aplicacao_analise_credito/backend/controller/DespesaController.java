package com.analisedecredito.aplicacao_analise_credito.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.DespesaDto;
import com.analisedecredito.aplicacao_analise_credito.backend.dto.DespesaReadDto;
import com.analisedecredito.aplicacao_analise_credito.backend.dto.PatrimonioReadDto;
import com.analisedecredito.aplicacao_analise_credito.backend.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.backend.service.DespesaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/despesa")
@CrossOrigin(origins = "http://localhost:5173")
public class DespesaController {

    @Autowired
    DespesaService service;

    @Operation(summary = "Encontra uma despesa por ID", description = "Este endpoint retorna uma despesa com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID da despesa", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Despesa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    })
    @GetMapping("/{id}")
    public DespesaReadDto findById(
            @PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(summary = "Lista todas as despesas", description = "Este endpoint retorna uma lista de todas as despesas cadastradas.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de despesas retornada com sucesso")
    })
    @GetMapping("/list")
    public List<DespesaReadDto> list() {
        return service.list();
    }

    @Operation(summary = "Retorna uma lista de patrimônios por cliente", description = "Este endpoint retorna uma lista de patrimônios associados a um cliente específico com base no ID do cliente.", parameters = @Parameter(name = "id", description = "ID do cliente", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Lista de patrimônios retornada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PatrimonioReadDto.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/idCliente/{id}")
    public List<DespesaReadDto> findByIdCliente(@PathVariable("id") Integer id) {
        return service.findByIdCliente(id);
    }

    @Operation(summary = "Cria uma nova despesa", description = "Este endpoint cria uma nova despesa com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da despesa a ser criada", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DespesaDto.class))), responses = {
            @ApiResponse(responseCode = "201", description = "Despesa criada com sucesso")
    })
    @PostMapping
    public DespesaDto createDespesa(@RequestBody DespesaDto despesaDto) {
        // Verifica se o despesaDto recebido não é nulo e se o cliente está presente
        // if (despesaDto == null || despesaDto.getCliente() == null) {
        // throw new RuntimeException("Dados de cliente inválidos na requisição.");
        // }

        // Chama o serviço para criar a despesa
        return service.create(despesaDto);
    }

    @Operation(summary = "Atualiza uma despesa existente", description = "Este endpoint atualiza os dados de uma despesa existente com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados da despesa", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DespesaDto.class))), responses = {
            @ApiResponse(responseCode = "200", description = "Despesa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    })
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

    @Operation(summary = "Remove uma despesa pelo ID", description = "Este endpoint remove uma despesa com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID da despesa a ser removida", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Despesa removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}