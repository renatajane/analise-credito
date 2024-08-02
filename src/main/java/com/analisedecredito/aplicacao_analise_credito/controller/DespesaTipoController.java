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

import com.analisedecredito.aplicacao_analise_credito.dto.DespesaTipoDto;
import com.analisedecredito.aplicacao_analise_credito.service.DespesaTipoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/despesa-tipo")
public class DespesaTipoController {

    @Autowired
    DespesaTipoService service;

    @Operation(
        summary = "Encontra um tipo de despesa por ID",
        description = "Este endpoint retorna um tipo de despesa com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do tipo de despesa", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Tipo de despesa encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de despesa não encontrado")
        }
    )
    @GetMapping("/{id}")
    public DespesaTipoDto findById(
        @PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(
        summary = "Lista todos os tipos de despesas",
        description = "Este endpoint retorna uma lista de todos os tipos de despesas cadastrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de despesas retornada com sucesso")
        }
    )
    @GetMapping("/list")
    public List<DespesaTipoDto> list() {
        return service.list();
    }

    @Operation(
        summary = "Cria um novo tipo de despesa",
        description = "Este endpoint cria um novo tipo de despesa com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do tipo de despesa a ser criado",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DespesaTipoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Tipo de despesa criado com sucesso")
        }
    )
    @PostMapping
    public void create(@RequestBody DespesaTipoDto despesaTipoDto) {
        if (despesaTipoDto.getDescricaoDespesaTipo() == null ||
                despesaTipoDto.getDescricaoDespesaTipo().isEmpty()) {
            throw new IllegalArgumentException("É necessário inserir uma descrição.");
        }
        service.create(despesaTipoDto);
    }

    @Operation(
        summary = "Atualiza um tipo de despesa existente",
        description = "Este endpoint atualiza os dados de um tipo de despesa existente com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados do tipo de despesa",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DespesaTipoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Tipo de despesa atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Tipo de despesa não encontrado")
        }
    )
    @PutMapping
    private void update(@RequestBody DespesaTipoDto despesaTipoDto){
        service.update(despesaTipoDto);
    }

    @Operation(
        summary = "Remove um tipo de despesa por ID",
        description = "Este endpoint remove um tipo de despesa com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do tipo de despesa a ser removido", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Tipo de despesa removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de despesa não encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}