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

import com.analisedecredito.aplicacao_analise_credito.dto.PatrimonioTipoDto;
import com.analisedecredito.aplicacao_analise_credito.repository.PatrimonioTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.service.PatrimonioTipoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/patrimonio-tipo")
public class PatrimonioTipoController {

    @Autowired
    PatrimonioTipoRepository repository;

    @Autowired
    PatrimonioTipoService service;

    @Operation(
        summary = "Retorna um tipo de patrimônio de acordo com o id",
        description = "Este endpoint retorna um tipo de patrimônio com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do tipo de patrimônio", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Tipo de patrimônio encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de patrimônio não encontrado")
        }
    )
    @GetMapping("/{id}")
    private PatrimonioTipoDto findById(@PathVariable("id") Integer id) {
        return new PatrimonioTipoDto(repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tipo de patrimônio não encontrado")));
    }

    @Operation(
        summary = "Retorna uma lista de todos os tipos de patrimônio cadastrados",
        description = "Este endpoint retorna uma lista de todos os tipos de patrimônio cadastrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de patrimônio retornada com sucesso")
        }
    )
    @GetMapping("/list")
    private List<PatrimonioTipoDto> list() {
        return service.list();
    }

    @Operation(
        summary = "Cria um novo tipo de patrimônio com base nos dados fornecidos",
        description = "Este endpoint cria um novo tipo de patrimônio com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do tipo de patrimônio a ser criado",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PatrimonioTipoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Tipo de patrimônio criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    @PostMapping("/create")
    public void create(@RequestBody PatrimonioTipoDto patrimonioTipoDto) {
        if (patrimonioTipoDto.getDescricaoPatrimonioTipo() == null || patrimonioTipoDto.getDescricaoPatrimonioTipo().isEmpty()) {
            throw new IllegalArgumentException("É necessário inserir uma descrição.");
        }
        service.create(patrimonioTipoDto);
    }

    @Operation(
        summary = "Atualiza os dados de um tipo de patrimônio existente",
        description = "Este endpoint atualiza os dados de um tipo de patrimônio existente com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do tipo de patrimônio a ser atualizado",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PatrimonioTipoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Tipo de patrimônio atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    @PutMapping("/update")
    public void update(@RequestBody PatrimonioTipoDto patrimonioTipoDto) {
        service.update(patrimonioTipoDto);
    }

    @Operation(
        summary = "Remove um tipo de patrimônio pelo id",
        description = "Este endpoint remove um tipo de patrimônio com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do tipo de patrimônio", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Tipo de patrimônio removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de patrimônio não encontrado")
        }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}