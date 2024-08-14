package com.analisedecredito.aplicacao_analise_credito.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.EmprestimoObjetivoDto;
import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoObjetivo;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.EmprestimoObjetivoRepository;

@Service
public class EmprestimoObjetivoService {
    
    @Autowired
    EmprestimoObjetivoRepository repository;

    /* Retorna o objetivo do emprésitimo de acordo com o id */
    public EmprestimoObjetivoDto findById(Integer id){
        return new EmprestimoObjetivoDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todos os tipos de objetivo de emprestimo cadastrados */
    public List<EmprestimoObjetivoDto> list(){
        List<EmprestimoObjetivo> emprestimoObjetivos = repository.findAll();
        return emprestimoObjetivos.stream().map(EmprestimoObjetivoDto::new).toList();        
    }

    /* Cria um novo objetivo de empréstimo com base nos dados fornecidos */
    public void create(EmprestimoObjetivoDto emprestimoObjetivoDto){
        EmprestimoObjetivo emprestimosObjetivos = new EmprestimoObjetivo(emprestimoObjetivoDto);
        repository.save(emprestimosObjetivos);
    }

    /* Atualiza os dados de um objetivo de empréstimo existente */
    public EmprestimoObjetivoDto update(EmprestimoObjetivoDto emprestimoObjetivoDto){
        EmprestimoObjetivo emprestimoObjetivo = new EmprestimoObjetivo(emprestimoObjetivoDto);
        return new EmprestimoObjetivoDto(repository.save(emprestimoObjetivo));
    }

    /* Remove um dado de objetivo de empréstimo pelo id */
    public void delete(Integer id){
        EmprestimoObjetivo emprestimoObjetivo = repository.findById(id).get();
        repository.delete(emprestimoObjetivo);
    }
}
