package com.analisedecredito.aplicacao_analise_credito.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.EmprestimoUrgenciaDto;
import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoUrgencia;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.EmprestimoUrgenciaRepository;

@Service
public class EmprestimoUrgenciaService {

    @Autowired
    EmprestimoUrgenciaRepository repository;

    /* Retorna uma urgência de empréstimo de acordo com o id */
    public EmprestimoUrgenciaDto findById(Integer id) {
        return new EmprestimoUrgenciaDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todos os tipos de urgência de empréstimo cadastrados */
    public List<EmprestimoUrgenciaDto> list() {
        List<EmprestimoUrgencia> listaUrgencias = repository.findAll();
        return listaUrgencias.stream().map(EmprestimoUrgenciaDto::new).toList();
    }

    /* Cria um novo urgência empréstimo com base nos dados fornecidos */
    public void create(EmprestimoUrgenciaDto emprestimoUrgenciaDto) {
        EmprestimoUrgencia emprestimoUrgencia = new EmprestimoUrgencia(emprestimoUrgenciaDto);
        repository.save(emprestimoUrgencia);
    }

    /* Atualiza os dados de empréstimo urgência existente */
    public EmprestimoUrgenciaDto update(EmprestimoUrgenciaDto emprestimoUrgenciaDto) {
        EmprestimoUrgencia emprestimoUrgencia = new EmprestimoUrgencia(emprestimoUrgenciaDto);
        return new EmprestimoUrgenciaDto(repository.save(emprestimoUrgencia));
    }

    /* Remove um dado empréstimo urgência pelo id */
    public void delete(Integer id) {
        EmprestimoUrgencia emprestimoUrgencia = repository.findById(id).get();
        repository.delete(emprestimoUrgencia);
    }
}
