package com.analisedecredito.aplicacao_analise_credito.backend.controller;

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

import com.analisedecredito.aplicacao_analise_credito.backend.dto.ClienteDto;
import com.analisedecredito.aplicacao_analise_credito.backend.dto.ClienteReadDto;
import com.analisedecredito.aplicacao_analise_credito.backend.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.backend.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteService service;

    @Operation(summary = "Encontra um cliente por ID", description = "Este endpoint retorna um cliente com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID do cliente", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ClienteReadDto findById(
            @PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(summary = "Lista todos os clientes", description = "Este endpoint retorna uma lista de todos os clientes cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    })
    @GetMapping("/list")
    public List<ClienteReadDto> list() {
        return service.list();
    }

    @GetMapping("/cpf/{cpf}") 
    public ClienteReadDto findByCpf(
            @PathVariable("cpf") String cpf) {
        return service.findByCpf(cpf);
    }


    @Operation(summary = "Cria um novo cliente", description = "Este endpoint cria um novo cliente com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do cliente a ser criado", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ClienteDto.class))), responses = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    })
    @PostMapping
    public ResponseEntity<String> create(@RequestBody ClienteDto clienteDto) {
            try {
        service.create(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente criado com sucesso.");
    } catch (Exception e) {
        // Log do erro
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao criar o cliente.");
    }
    }

    @Operation(summary = "Atualiza um cliente existente", description = "Este endpoint atualiza os dados de um cliente existente com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do cliente", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ClienteDto.class))), responses = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping
    public ResponseEntity<ClienteDto> update(@RequestBody ClienteDto clienteDto) {
        try {
            Integer id = clienteDto.getIdCliente();
            ClienteDto updatedCliente = service.update(id, clienteDto);
            return ResponseEntity.ok(updatedCliente);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Soma a renda total do cliente por ID", description = "Este endpoint retorna a soma da renda total de um cliente com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID do cliente", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Renda total do cliente retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/renda/{id}")
    public Double getRendaCliente(@PathVariable("id") Integer id) {
        return service.somaRenda(id);
    }

    @Operation(summary = "Soma o patrimônio total do cliente por ID", description = "Este endpoint retorna a soma do patrimônio total de um cliente com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID do cliente", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Patrimônio total do cliente retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/patrimonio/{id}")
    public Double getPatrimonioCliente(@PathVariable("id") Integer id) {
        return service.somaPatrimonio(id);
    }

    @Operation(summary = "Soma a despesa total do cliente por ID", description = "Este endpoint retorna a soma das despesas totais de um cliente com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID do cliente", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Despesa total do cliente retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/despesa/{id}")
    public Double getDespesaCliente(@PathVariable("id") Integer id) {
        return service.calculaDespesaTotal(id);
    }

    @Operation(summary = "Remove um cliente pelo ID", description = "Este endpoint remove um cliente com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID do cliente a ser removido", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Cliente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}