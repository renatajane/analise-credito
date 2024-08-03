package com.analisedecredito.aplicacao_analise_credito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoReadDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoRequisicaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/emprestimo-requisicao")
public class EmprestimoRequisicaoController {

    @Autowired
    EmprestimoRequisicaoService service;

    @Operation(
        summary = "Encontra uma requisição de empréstimo por ID",
        description = "Este endpoint retorna uma requisição de empréstimo com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID da requisição de empréstimo", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Requisição de empréstimo encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição de empréstimo não encontrada")
        }
    )
    @GetMapping("/{id}")
    public EmprestimoRequisicaoReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(
        summary = "Lista todas as requisições de empréstimos",
        description = "Este endpoint retorna uma lista de todas as requisições de empréstimos cadastradas.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de requisições de empréstimos retornada com sucesso")
        }
    )
    @GetMapping("/list")
    public List<EmprestimoRequisicaoReadDto> list() {
        return service.list();
    }

    @Operation(
        summary = "Cria uma nova requisição de empréstimo",
        description = "Este endpoint cria uma nova requisição de empréstimo com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da requisição de empréstimo a ser criada",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoRequisicaoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Requisição de empréstimo criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    @PostMapping
    public void create(@RequestBody EmprestimoRequisicaoDto emprestimoRequisicaoDto) {
        service.create(emprestimoRequisicaoDto);
    }

    @Operation(
        summary = "Atualiza uma requisição de empréstimo existente",
        description = "Este endpoint atualiza os dados de uma requisição de empréstimo existente com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados da requisição de empréstimo",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoRequisicaoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Requisição de empréstimo atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Requisição de empréstimo não encontrada")
        }
    )
    @PutMapping()
    public ResponseEntity<EmprestimoRequisicaoDto> updateEmprestimo(
            @RequestBody EmprestimoRequisicaoDto emprestimoRequisicaoDto) {

        try {
            Integer id = emprestimoRequisicaoDto.getIdRequisicao();
            EmprestimoRequisicaoDto updatedEmprestimo = service.update(id, emprestimoRequisicaoDto);
            return ResponseEntity.ok(updatedEmprestimo);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Remove uma requisição de empréstimo por ID",
        description = "Este endpoint remove uma requisição de empréstimo com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID da requisição de empréstimo a ser removida", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Requisição de empréstimo removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição de empréstimo não encontrada")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}