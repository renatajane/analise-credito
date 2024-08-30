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

import com.analisedecredito.aplicacao_analise_credito.backend.dto.RendaFonteDto;
import com.analisedecredito.aplicacao_analise_credito.backend.dto.RendaFonteReadDto;
import com.analisedecredito.aplicacao_analise_credito.backend.service.RendaFonteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/renda-fonte")
@CrossOrigin(origins = "http://localhost:5173")
public class RendaFonteController {

    @Autowired
    RendaFonteService service;

    @Operation(summary = "Retorna uma fonte de renda de acordo com o id", description = "Este endpoint retorna uma fonte de renda com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID da fonte de renda", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Fonte de renda encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fonte de renda não encontrada")
    })
    @GetMapping("/{id}")
    public RendaFonteReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    
    @Operation(summary = "Retorna uma lista de fontes de renda de acordo com o id do cliente", description = "Este endpoint retorna uma lista de fontes de renda com base no ID do cliente fornecido.", parameters = @Parameter(name = "id", description = "ID do cliente", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Lista de fontes de renda encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma fonte de renda encontrada para o cliente especificado")
    })
    @GetMapping("/idCliente/{id}")
    public List<RendaFonteReadDto> findByIdCliente(@PathVariable("id") Integer id) {
        return service.findByIdCliente(id);
    }

    @Operation(summary = "Retorna uma lista de todas as fontes de renda cadastradas", description = "Este endpoint retorna uma lista de todas as fontes de renda cadastradas.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de fontes de renda retornada com sucesso")
    })
    @GetMapping
    public List<RendaFonteReadDto> list() {
        return service.list();
    }

    @Operation(summary = "Cria uma nova fonte de renda com base nos dados fornecidos", description = "Este endpoint cria uma nova fonte de renda com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da fonte de renda a ser criada", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RendaFonteDto.class))), responses = {
            @ApiResponse(responseCode = "201", description = "Fonte de renda criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public void create(@RequestBody RendaFonteDto rendaFonteDto) {
        service.create(rendaFonteDto);
    }

    @Operation(summary = "Atualiza os dados de uma fonte de renda existente", description = "Este endpoint atualiza os dados de uma fonte de renda existente com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da fonte de renda a ser atualizada", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RendaFonteDto.class))), responses = {
            @ApiResponse(responseCode = "200", description = "Fonte de renda atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping
    public ResponseEntity<RendaFonteDto> update(@RequestBody RendaFonteDto rendaFonteDto) {
        Integer id = rendaFonteDto.getIdRendaFonte();
        RendaFonteDto updatedFonteRenda = service.update(id, rendaFonteDto);
        return ResponseEntity.ok(updatedFonteRenda);
    }

    @Operation(summary = "Remove uma fonte de renda pelo id", description = "Este endpoint remove uma fonte de renda com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID da fonte de renda", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Fonte de renda removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fonte de renda não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}