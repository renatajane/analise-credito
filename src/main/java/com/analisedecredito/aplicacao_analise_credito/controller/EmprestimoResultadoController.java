package com.analisedecredito.aplicacao_analise_credito.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoReadDto;
import com.analisedecredito.aplicacao_analise_credito.service.EmprestimoResultadoService;
import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/emprestimo-resultado")
public class EmprestimoResultadoController {

    @Autowired
    EmprestimoResultadoService service;

    /* Retorna um resultado de empréstimo de acordo com o id */
    @GetMapping("/{id}")
    public EmprestimoResultadoReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de resultados de empréstimos cadastrados */
    @GetMapping("/list")
    public List<EmprestimoResultadoReadDto> list() {
        return service.list();
    }

    /* Retorna um pdf com base no CPF do cliente */
    @GetMapping("/pdf/{cpf}/{id}")
    public void getPdfByCpf(@PathVariable("cpf") String cpf, @PathVariable("id") Integer id, HttpServletResponse response) throws DocumentException, IOException {
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

    /* Cria um novo resultado de empréstimo com base nos dados fornecidos */
    @PostMapping
    public void create(@RequestBody EmprestimoResultadoDto emprestimoResultadoDto) {
        service.create(emprestimoResultadoDto);
    }

    /* Atualiza os dados de um resultado de empréstimo existente */
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

    /* Remove um dado de resultado de empréstimo pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
