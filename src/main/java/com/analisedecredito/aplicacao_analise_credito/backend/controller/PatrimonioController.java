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

import com.analisedecredito.aplicacao_analise_credito.backend.dto.PatrimonioDto;
import com.analisedecredito.aplicacao_analise_credito.backend.dto.PatrimonioReadDto;
import com.analisedecredito.aplicacao_analise_credito.backend.service.PatrimonioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/patrimonio")
@CrossOrigin(origins = "http://localhost:5173")
public class PatrimonioController {

    @Autowired
    PatrimonioService service;

    @Operation(summary = "Retorna um patrimônio de acordo com o id", description = "Este endpoint retorna um patrimônio com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID do patrimônio", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Patrimônio encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Patrimônio não encontrado")
    })
    @GetMapping("/{id}")
    public PatrimonioReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(summary = "Retorna uma lista de patrimônios cadastrados", description = "Este endpoint retorna uma lista de todos os patrimônios cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de patrimônios retornada com sucesso")
    })
    @GetMapping("/list")
    public List<PatrimonioReadDto> list() {
        return service.list();
    }

    @Operation(summary = "Retorna uma lista de patrimônios por cliente", description = "Este endpoint retorna uma lista de patrimônios associados a um cliente específico com base no ID do cliente.", parameters = @Parameter(name = "id", description = "ID do cliente", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Lista de patrimônios retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/idCliente/{id}")
    public List<PatrimonioReadDto> findByIdCliente(@PathVariable("id") Integer id) {
        return service.findByIdCliente(id);
    }

    @Operation(summary = "Cria um novo patrimônio com base nos dados fornecidos", description = "Este endpoint cria um novo patrimônio com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do patrimônio a ser criado", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PatrimonioDto.class))), responses = {
            @ApiResponse(responseCode = "201", description = "Patrimônio criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public void create(@RequestBody PatrimonioDto patrimonioDto) {
        service.create(patrimonioDto);
    }

    @Operation(summary = "Atualiza o patrimônio com base nos dados fornecidos", description = "Este endpoint atualiza um patrimônio existente com base no ID fornecido e nos dados do DTO.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do patrimônio a ser atualizado", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PatrimonioDto.class))), responses = {
            @ApiResponse(responseCode = "200", description = "Patrimônio atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Patrimônio não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody PatrimonioDto patrimonioDto) {
        service.update(id, patrimonioDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove um patrimônio pelo id", description = "Este endpoint remove um patrimônio com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID do patrimônio", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Patrimônio removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Patrimônio não encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}