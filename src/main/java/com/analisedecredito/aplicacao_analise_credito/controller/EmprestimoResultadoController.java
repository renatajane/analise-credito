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
    EmprestimoRe
    sultadoService service;

    /* Retorna um empréstimo resultado de acordo com o id */
    @GetMapping("/{id}")
    public EmprestimoResultadoReadDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /* Retorna uma lista de resultados de empréstimo cadastrados */
    @GetMapping("/list")
    public List<EmprestimoResultadoReadDto> list() {
        return service.list();
    }

    @GetMapping("/pdf/{id}")
    public void teste(@PathVariable("id") Integer id, HttpServletResponse response) 
        throws DocumentException {

        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");

        response.setContentType("appliction/pdf");
        var vo = service.findById(id);
        ByteArrayOutputStream pdf = service.getPdf(vo);

        if (pdf != null) {
            try (var output = response.getOutPutStream()) {
                pdf.writeTo(output);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        service.imprimir(id);
    }

    /* Cria um novo resultado de empréstimo com base nos dados fornecidos */
    @PostMapping
    public void Create(@RequestBody EmprestimoResultadoDto emprestimoResultadoDto) {
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

    /* Remove um resultado de empréstimo pelo id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
