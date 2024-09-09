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

import com.analisedecredito.aplicacao_analise_credito.dto.ModalidadePagamentoDto;
import com.analisedecredito.aplicacao_analise_credito.service.ModalidadePagamentoService;

@RestController
@RequestMapping("/modalidade-pagamento")
public class ModalidadePagamentoController {

    @Autowired
    ModalidadePagamentoService service;

    @GetMapping("/{id}")
    public ModalidadePagamentoDto findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @GetMapping("/list")
    public List<ModalidadePagamentoDto> list() {
        return service.list();
    }

    @PostMapping
    public void create(@RequestBody ModalidadePagamentoDto modalidadePagamentoDto) {
        service.create(modalidadePagamentoDto);
    }

    @PutMapping
    private void update(@RequestBody ModalidadePagamentoDto modalidadePagamentoDto) {
        service.update(modalidadePagamentoDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
