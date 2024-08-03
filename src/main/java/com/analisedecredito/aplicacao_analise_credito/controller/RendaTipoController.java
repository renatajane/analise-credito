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

import com.analisedecredito.aplicacao_analise_credito.dto.RendaTipoDto;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.service.RendaTipoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/renda-tipo")
public class RendaTipoController {

    @Autowired
    RendaTipoRepository repository;

    @Autowired
    RendaTipoService service;

    @Operation(
        summary = "Retorna um tipo de renda de acordo com o id",
        description = "Este endpoint retorna um tipo de renda com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do tipo de renda", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Tipo de renda encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de renda não encontrado")
        }
    )
    @GetMapping("/{id}")
    public RendaTipoDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(
        summary = "Retorna uma lista de todos os tipos de renda cadastrados",
        description = "Este endpoint retorna uma lista de todos os tipos de renda cadastrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de renda retornada com sucesso")
        }
    )
    @GetMapping("/list")
    public List<RendaTipoDto> list() {
        return service.list();
    }

    @Operation(
        summary = "Cria um novo tipo de renda com base nos dados fornecidos",
        description = "Este endpoint cria um novo tipo de renda com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do tipo de renda a ser criado",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RendaTipoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Tipo de renda criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    @PostMapping
    public void create(@RequestBody RendaTipoDto rendaTipoDto) {
        if (rendaTipoDto.getDescricaoRendaTipo() == null || rendaTipoDto.getDescricaoRendaTipo().isEmpty()) {
            throw new IllegalArgumentException("Descrição da renda tipo não pode ser nula ou vazia");
        }
        service.create(rendaTipoDto);
    }

    @Operation(
        summary = "Atualiza os dados de um tipo de renda existente",
        description = "Este endpoint atualiza os dados de um tipo de renda existente com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do tipo de renda a ser atualizado",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RendaTipoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Tipo de renda atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    @PutMapping
    public RendaTipoDto update(@RequestBody RendaTipoDto rendaTipoDto) {
        return service.update(rendaTipoDto);
    }

    @Operation(
        summary = "Remove um tipo de renda pelo id",
        description = "Este endpoint remove um tipo de renda com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do tipo de renda", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Tipo de renda removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de renda não encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}