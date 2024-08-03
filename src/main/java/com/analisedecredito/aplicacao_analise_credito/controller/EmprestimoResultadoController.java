package com.analisedecredito.aplicacao_analise_credito.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoReadDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoResultadoService;
import com.itextpdf.text.DocumentException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/emprestimo-resultado")
public class EmprestimoResultadoController {

    @Autowired
    EmprestimoResultadoService service;

    @Operation(
        summary = "Retorna um resultado de empréstimo de acordo com o id",
        description = "Este endpoint retorna um resultado de empréstimo com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do resultado de empréstimo", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Resultado de empréstimo encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Resultado de empréstimo não encontrado")
        }
    )
    @GetMapping("/{id}")
    public EmprestimoResultadoReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @Operation(
        summary = "Retorna uma lista de resultados de empréstimos cadastrados",
        description = "Este endpoint retorna uma lista de todos os resultados de empréstimos cadastrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de resultados de empréstimos retornada com sucesso")
        }
    )
    @GetMapping("/list")
    public List<EmprestimoResultadoReadDto> list() {
        return service.list();
    }

    @Operation(
        summary = "Retorna um PDF com base no CPF do cliente",
        description = "Este endpoint gera um PDF contendo os resultados de empréstimos de um cliente específico, baseado no CPF fornecido.",
        parameters = {
            @Parameter(name = "cpf", description = "CPF do cliente", example = "12345678900", required = true),
            @Parameter(name = "id", description = "ID do resultado de empréstimo", example = "1", required = true)
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "PDF gerado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Resultado de empréstimo não encontrado")
        }
    )
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

    @Operation(
        summary = "Retorna um PDF com base no período fornecido",
        description = "Este endpoint gera um PDF contendo os resultados de empréstimos dentro de um período especificado.",
        parameters = {
            @Parameter(name = "inicio", description = "Data de início no formato yyyy-MM-dd", example = "2023-01-01", required = true),
            @Parameter(name = "fim", description = "Data de término no formato yyyy-MM-dd", example = "2023-12-31", required = true)
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "PDF gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos")
        }
    )
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

    @Operation(
        summary = "Cria um novo resultado de empréstimo com base nos dados fornecidos",
        description = "Este endpoint cria um novo resultado de empréstimo com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do resultado de empréstimo a ser criado",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoResultadoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Resultado de empréstimo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    @PostMapping
    public void create(@RequestBody EmprestimoResultadoDto emprestimoResultadoDto) {
        service.create(emprestimoResultadoDto);
    }

    @Operation(
        summary = "Atualiza os dados de um resultado de empréstimo existente",
        description = "Este endpoint atualiza os dados de um resultado de empréstimo existente com base nos dados fornecidos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados atualizados do resultado de empréstimo",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmprestimoResultadoDto.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Resultado de empréstimo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Resultado de empréstimo não encontrado")
        }
    )
    @PutMapping
    public ResponseEntity<EmprestimoResultadoDto> update(@RequestBody EmprestimoResultadoDto emprestimoResultadoDto) {
        try {
            Integer id = emprestimoResultadoDto.getIdResultado();
            EmprestimoResultadoDto updatedResultado = service.update(id, emprestimoResultadoDto);
            return ResponseEntity.ok(updatedResultado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Remove um dado de resultado de empréstimo pelo id",
        description = "Este endpoint remove um resultado de empréstimo com base no ID fornecido.",
        parameters = @Parameter(name = "id", description = "ID do resultado de empréstimo", example = "1", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Resultado de empréstimo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Resultado de empréstimo não encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}

