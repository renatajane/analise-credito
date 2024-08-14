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

import com.analisedecredito.aplicacao_analise_credito.backend.dto.IofAtualDto;
import com.analisedecredito.aplicacao_analise_credito.backend.service.IofAtualService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/iof-atual")
public class IofAtualController {

    @Autowired
    IofAtualService service;

    @Operation(
        summary = "Retorna IOF de acordo com o id",
        description = "Este endpoint retorna um IOF com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do IOF", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "IOF encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "IOF não encontrado")
        }
    )
    @GetMapping("/{id}")
    public IofAtualDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(
        summary = "Retorna uma lista de IOFs cadastrados",
        description = "Este endpoint retorna uma lista de todos os IOFs cadastrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de IOFs retornada com sucesso")
        }
    )
    @GetMapping("/list")
    public List<IofAtualDto> list() {
        return service.list();
    }

    @Operation(
        summary = "Cria um novo IOF com base nos dados fornecidos",
        description = "Este endpoint cria um novo IOF com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do IOF a ser criado",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IofAtualDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "IOF criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    @PostMapping
    public void create(@RequestBody IofAtualDto iofAtualDto) {
        service.create(iofAtualDto);
    }

    @Operation(
        summary = "Atualiza os dados de IOF existente",
        description = "Este endpoint atualiza os dados de um IOF existente com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados do IOF",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IofAtualDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "IOF atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "IOF não encontrado")
        }
    )
    @PutMapping
    public IofAtualDto update(@RequestBody IofAtualDto iofAtualDto) {
        return service.update(iofAtualDto);
    }

    @Operation(
        summary = "Remove um IOF pelo id",
        description = "Este endpoint remove um IOF com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do IOF a ser removido", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "IOF removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "IOF não encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}