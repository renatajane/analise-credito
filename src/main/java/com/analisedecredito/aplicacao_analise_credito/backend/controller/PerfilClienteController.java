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

import com.analisedecredito.aplicacao_analise_credito.backend.dto.PerfilClienteDto;
import com.analisedecredito.aplicacao_analise_credito.backend.service.PerfilClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/perfil-cliente")
public class PerfilClienteController {

    @Autowired
    PerfilClienteService service;

    @Operation(summary = "Retorna um perfil de crédito de acordo com o id", description = "Este endpoint retorna um perfil de crédito com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID do perfil de crédito", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Perfil de crédito encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Perfil de crédito não encontrado")
    })
    @GetMapping("/{id}")
    public PerfilClienteDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(summary = "Retorna uma lista de perfis de crédito cadastrados", description = "Este endpoint retorna uma lista de perfis de crédito cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de perfis de crédito retornada com sucesso")
    })
    @GetMapping("/list")
    public List<PerfilClienteDto> list() {
        return service.list();
    }

    @Operation(summary = "Cria um novo perfil de crédito com base nos dados fornecidos", description = "Este endpoint cria um novo perfil de crédito com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do perfil de crédito a ser criado", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PerfilClienteDto.class))), responses = {
            @ApiResponse(responseCode = "201", description = "Perfil de crédito criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public void create(@RequestBody PerfilClienteDto perfilClienteDto) {
        service.create(perfilClienteDto);
    }

    @Operation(summary = "Atualiza os dados de um perfil de crédito existente com base nos dados fornecidos", description = "Este endpoint atualiza os dados de um perfil de crédito existente com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do perfil de crédito a ser atualizado", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PerfilClienteDto.class))), responses = {
            @ApiResponse(responseCode = "200", description = "Perfil de crédito atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping
    public PerfilClienteDto update(@RequestBody PerfilClienteDto perfilClienteDto) {
        return service.update(perfilClienteDto);
    }

    @Operation(summary = "Remove um perfil de crédito de acordo com o id", description = "Este endpoint remove um perfil de crédito com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID do perfil de crédito", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Perfil de crédito removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Perfil de crédito não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}