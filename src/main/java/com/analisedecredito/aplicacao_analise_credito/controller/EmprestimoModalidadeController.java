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

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoModalidadeDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoModalidadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/emprestimo-modalidade")
public class EmprestimoModalidadeController {

    @Autowired
    EmprestimoModalidadeService service;

    @Operation(
        summary = "Encontra uma modalidade de empréstimo por ID",
        description = "Este endpoint retorna uma modalidade de empréstimo com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID da modalidade de empréstimo", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Modalidade de empréstimo encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Modalidade de empréstimo não encontrada")
        }
    )
    @GetMapping("/{id}")
    private EmprestimoModalidadeDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(
        summary = "Lista todas as modalidades de empréstimos",
        description = "Este endpoint retorna uma lista de todas as modalidades de empréstimos cadastrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de modalidades de empréstimos retornada com sucesso")
        }
    )
    @GetMapping("/list")
    private List<EmprestimoModalidadeDto> list() {
        return service.list();
    }

    @Operation(
        summary = "Cria uma nova modalidade de empréstimo",
        description = "Este endpoint cria uma nova modalidade de empréstimo com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da modalidade de empréstimo a ser criada",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoModalidadeDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Modalidade de empréstimo criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Descrição da modalidade de empréstimo inválida")
        }
    )
    @PostMapping
    private void create(@RequestBody EmprestimoModalidadeDto emprestimoModalidadeDto) {
        if (emprestimoModalidadeDto.getDescricaoModalidade() == null ||
                emprestimoModalidadeDto.getDescricaoModalidade().isEmpty()) {
            throw new IllegalArgumentException("É preciso inserir uma descrição.");
        }
        service.create(emprestimoModalidadeDto);
    }

    @Operation(
        summary = "Atualiza uma modalidade de empréstimo existente",
        description = "Este endpoint atualiza os dados de uma modalidade de empréstimo existente com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados da modalidade de empréstimo",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoModalidadeDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Modalidade de empréstimo atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Modalidade de empréstimo não encontrada")
        }
    )
    @PutMapping
    private EmprestimoModalidadeDto update(@RequestBody EmprestimoModalidadeDto emprestimoModalidadeDto) {
        return service.update(emprestimoModalidadeDto);
    }

    @Operation(
        summary = "Remove uma modalidade de empréstimo por ID",
        description = "Este endpoint remove uma modalidade de empréstimo com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID da modalidade de empréstimo a ser removida", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Modalidade de empréstimo removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Modalidade de empréstimo não encontrada")
        }
    )
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}