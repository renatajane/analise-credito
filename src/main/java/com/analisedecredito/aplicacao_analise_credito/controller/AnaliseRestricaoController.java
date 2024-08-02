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

import com.analisedecredito.aplicacao_analise_credito.dto.AnaliseRestricaoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.AnaliseRestricaoReadDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.service.AnaliseRestricaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/analise-restricao")
public class AnaliseRestricaoController {

    @Autowired
    AnaliseRestricaoService service;

    @Operation(
        summary = "Encontra uma análise de restrição por ID",
        description = "Este endpoint retorna uma análise de restrição com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID da análise de restrição", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Análise de restrição encontrada"),
            @ApiResponse(responseCode = "404", description = "Análise de restrição não encontrada")
        }
    )
    @GetMapping("/{id}")
    public AnaliseRestricaoReadDto findById(
        @PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(
        summary = "Lista todas as análises de restrições",
        description = "Este endpoint retorna uma lista de todas as análises de restrições cadastradas.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de análises de restrições retornada com sucesso")
        }
    )
    @GetMapping("/list")
    public List<AnaliseRestricaoReadDto> list() {
        return service.list();
    }

    @Operation(
        summary = "Cria uma nova análise de restrição",
        description = "Este endpoint cria uma nova análise de restrição com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da análise de restrição a ser criada",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AnaliseRestricaoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Análise de restrição criada com sucesso")
        }
    )
    @PostMapping
    public void create(@RequestBody AnaliseRestricaoDto analiseRestricaoDto) {
        service.create(analiseRestricaoDto);
    }

    @Operation(
        summary = "Atualiza uma análise de restrição existente",
        description = "Este endpoint atualiza os dados de uma análise de restrição existente com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados da análise de restrição",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AnaliseRestricaoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Análise de restrição atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Análise de restrição não encontrada")
        }
    )
    @PutMapping
    public ResponseEntity<AnaliseRestricaoDto> update(@RequestBody AnaliseRestricaoDto analiseRestricaoDto) {
        try {
            Integer id = analiseRestricaoDto.getIdRestricao();
            AnaliseRestricaoDto updatedAnalise = service.update(id, analiseRestricaoDto);
            return ResponseEntity.ok(updatedAnalise);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Remove uma análise de restrição pelo ID",
        description = "Este endpoint remove uma análise de restrição com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID da análise de restrição a ser removida", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Análise de restrição removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Análise de restrição não encontrada")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
