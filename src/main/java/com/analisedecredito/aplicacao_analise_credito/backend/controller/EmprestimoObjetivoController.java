package com.analisedecredito.aplicacao_analise_credito.backend.controller;

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

import com.analisedecredito.aplicacao_analise_credito.backend.dto.EmprestimoObjetivoDto;
import com.analisedecredito.aplicacao_analise_credito.backend.service.EmprestimoObjetivoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/emprestimo-objetivo")
public class EmprestimoObjetivoController {
    
    @Autowired
    EmprestimoObjetivoService service;

    @Operation(
        summary = "Encontra um objetivo de empréstimo por ID",
        description = "Este endpoint retorna um objetivo de empréstimo com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do objetivo de empréstimo", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Objetivo de empréstimo encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Objetivo de empréstimo não encontrado")
        }
    )
    @GetMapping("/{id}")
    public EmprestimoObjetivoDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(
        summary = "Lista todos os objetivos de empréstimo",
        description = "Este endpoint retorna uma lista de todos os objetivos de empréstimo cadastrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de objetivos de empréstimo retornada com sucesso")
        }
    )
    @GetMapping("/list")
    public List<EmprestimoObjetivoDto> list() {
        return service.list();
    }

    @Operation(
        summary = "Cria um novo objetivo de empréstimo",
        description = "Este endpoint cria um novo objetivo de empréstimo com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do objetivo de empréstimo a ser criado",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoObjetivoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Objetivo de empréstimo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Descrição do objetivo de empréstimo inválida")
        }
    )
    @PostMapping
    public void create(@RequestBody EmprestimoObjetivoDto emprestimoObjetivoDto) {
        if (emprestimoObjetivoDto.getDescricaoObjetivo() == null ||
                emprestimoObjetivoDto.getDescricaoObjetivo().isEmpty()) {
            throw new IllegalArgumentException("É necessário inserir uma descrição.");
        }
        service.create(emprestimoObjetivoDto);
    }
    
    @Operation(
        summary = "Atualiza um objetivo de empréstimo existente",
        description = "Este endpoint atualiza os dados de um objetivo de empréstimo existente com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados do objetivo de empréstimo",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoObjetivoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Objetivo de empréstimo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Objetivo de empréstimo não encontrado")
        }
    )
    @PutMapping
    public EmprestimoObjetivoDto update(@RequestBody EmprestimoObjetivoDto emprestimoObjetivoDto) {
        return service.update(emprestimoObjetivoDto);
    }

    @Operation(
        summary = "Remove um objetivo de empréstimo por ID",
        description = "Este endpoint remove um objetivo de empréstimo com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do objetivo de empréstimo a ser removido", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Objetivo de empréstimo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Objetivo de empréstimo não encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}