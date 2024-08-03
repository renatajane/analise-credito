package com.analisedecredito.aplicacao_analise_credito.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoReadDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoRequisicaoService;
import com.itextpdf.text.DocumentException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/emprestimo-requisicao")
public class EmprestimoRequisicaoController {

    @Autowired
    EmprestimoRequisicaoService service;

    @Operation(summary = "Encontra uma requisição de empréstimo por ID", description = "Este endpoint retorna uma requisição de empréstimo com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID da requisição de empréstimo", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Requisição de empréstimo encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição de empréstimo não encontrada")
    })
    @GetMapping("/{id}")
    public EmprestimoRequisicaoReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(summary = "Lista todas as requisições de empréstimos", description = "Este endpoint retorna uma lista de todas as requisições de empréstimos cadastradas.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de requisições de empréstimos retornada com sucesso")
    })
    @GetMapping("/list")
    public List<EmprestimoRequisicaoReadDto> list() {
        return service.list();
    }

    @Operation(summary = "Cria uma nova requisição de empréstimo", description = "Este endpoint cria uma nova requisição de empréstimo com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da requisição de empréstimo a ser criada", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoRequisicaoDto.class))), responses = {
            @ApiResponse(responseCode = "201", description = "Requisição de empréstimo criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public void create(@RequestBody EmprestimoRequisicaoDto emprestimoRequisicaoDto) {
        service.create(emprestimoRequisicaoDto);
    }

    @Operation(summary = "Atualiza uma requisição de empréstimo existente", description = "Este endpoint atualiza os dados de uma requisição de empréstimo existente com base nos dados fornecidos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados da requisição de empréstimo", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoRequisicaoDto.class))), responses = {
            @ApiResponse(responseCode = "200", description = "Requisição de empréstimo atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Requisição de empréstimo não encontrada")
    })
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

    @Operation(summary = "Remove uma requisição de empréstimo por ID", description = "Este endpoint remove uma requisição de empréstimo com base no ID fornecido.", parameters = @Parameter(name = "id", description = "ID da requisição de empréstimo a ser removida", example = "1", required = true), responses = {
            @ApiResponse(responseCode = "200", description = "Requisição de empréstimo removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição de empréstimo não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Retorna um PDF com base no CPF do cliente", description = "Este endpoint gera um PDF contendo os resultados de empréstimos de um cliente específico, baseado no CPF fornecido.", parameters = {
            @Parameter(name = "cpf", description = "CPF do cliente", example = "12345678900", required = true),
            @Parameter(name = "id", description = "ID do resultado de empréstimo", example = "1", required = true)
    }, responses = {
            @ApiResponse(responseCode = "200", description = "PDF gerado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Resultado de empréstimo não encontrado")
    })
    @GetMapping("/pdf/{cpf}/{id}")
    public void getPdfByCpf(@PathVariable("cpf") String cpf, @PathVariable("id") Integer id,
            HttpServletResponse response) throws DocumentException, IOException {
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType("application/pdf");

        ByteArrayOutputStream pdf = service.geraPdfCpf(cpf, id);

        if (pdf != null) {
            response.setContentLength(pdf.size());
            pdf.writeTo(response.getOutputStream());
            response.getOutputStream().flush();
        }
    }

    @Operation(summary = "Retorna um PDF com base no período fornecido", description = "Este endpoint gera um PDF contendo os resultados de empréstimos dentro de um período especificado.", parameters = {
            @Parameter(name = "inicio", description = "Data de início no formato yyyy-MM-dd", example = "2023-01-01", required = true),
            @Parameter(name = "fim", description = "Data de término no formato yyyy-MM-dd", example = "2023-12-31", required = true)
    }, responses = {
            @ApiResponse(responseCode = "200", description = "PDF gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos")
    })
    @GetMapping("/pdf/periodo")
    public void getPdfByPeriod(
            @RequestParam("inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
            @RequestParam("fim") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fim,
            HttpServletResponse response) throws DocumentException, IOException {
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType("application/pdf");

        ByteArrayOutputStream pdf = service.geraPdfPorPeriodo(inicio, fim);

        if (pdf != null) {
            response.setContentLength(pdf.size());
            pdf.writeTo(response.getOutputStream());
            response.getOutputStream().flush();
        }
    }
}