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

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoUrgenciaDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoUrgenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/emprestimo-urgencia")
public class EmprestimoUrgenciaController {

    @Autowired
    EmprestimoUrgenciaService service;

    @Operation(
        summary = "Retorna uma urgência de empréstimo de acordo com o id",
        description = "Este endpoint retorna uma urgência de empréstimo com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID da urgência de empréstimo", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Urgência de empréstimo encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Urgência de empréstimo não encontrada")
        }
    )
    @GetMapping("/{id}")
    public EmprestimoUrgenciaDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(
        summary = "Retorna uma lista de todos os tipos de urgência de empréstimo cadastrados",
        description = "Este endpoint retorna uma lista de todos os tipos de urgência de empréstimo cadastrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de urgências de empréstimo retornada com sucesso")
        }
    )
    @GetMapping("/list")
    public List<EmprestimoUrgenciaDto> list() {
        return service.list();
    }

    @Operation(
        summary = "Cria um novo urgência empréstimo com base nos dados fornecidos",
        description = "Este endpoint cria uma nova urgência de empréstimo com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da urgência de empréstimo a ser criada",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoUrgenciaDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Urgência de empréstimo criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    @PostMapping
    public void create(@RequestBody EmprestimoUrgenciaDto emprestimoUrgenciaDto) {
        if (emprestimoUrgenciaDto.getPrazoUrgencia() == null ||
                emprestimoUrgenciaDto.getPrazoUrgencia().isEmpty()) {
            throw new IllegalArgumentException("É necessário inserir um prazo.");
        }
        service.create(emprestimoUrgenciaDto);
    }

    @Operation(
        summary = "Atualiza os dados de urgência empréstimo existente",
        description = "Este endpoint atualiza os dados de uma urgência de empréstimo existente com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados da urgência de empréstimo",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoUrgenciaDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Urgência de empréstimo atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Urgência de empréstimo não encontrada")
        }
    )
    @PutMapping
    public EmprestimoUrgenciaDto update(@RequestBody EmprestimoUrgenciaDto emprestimoUrgenciaDto) {
        return service.update(emprestimoUrgenciaDto);
    }

    @Operation(
        summary = "Remove um dado de urgência de empréstimo pelo id",
        description = "Este endpoint remove uma urgência de empréstimo com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID da urgência de empréstimo", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Urgência de empréstimo removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Urgência de empréstimo não encontrada")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}