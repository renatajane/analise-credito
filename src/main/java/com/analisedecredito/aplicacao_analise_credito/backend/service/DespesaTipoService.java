package com.analisedecredito.aplicacao_analise_credito.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.DespesaTipoDto;
import com.analisedecredito.aplicacao_analise_credito.backend.model.DespesaTipo;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.DespesaTipoRepository;

@Service
public class DespesaTipoService {
    
    @Autowired
    DespesaTipoRepository repository;

    /* Retorna um tipo de despesa de acordo com o id */
    public DespesaTipoDto findById(Integer id){
        return new DespesaTipoDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todos os tipos de despesa cadastrados */
    public List<DespesaTipoDto> list(){
        List<DespesaTipo> despesaTipos = repository.findAll();
        return despesaTipos.stream().map(DespesaTipoDto::new).toList();
    }

    /* Cria um novo tipo de despesa com base nos dados fornecidos */
    public void create(DespesaTipoDto despesaTipoDto){
        DespesaTipo despesaTipo = new DespesaTipo(despesaTipoDto);
        repository.save(despesaTipo);
    }

    /* Atualiza os dados de um tipo de despesa existente com base nos dados fornecidos */
    public DespesaTipoDto update(DespesaTipoDto despesaTipoDto){
        DespesaTipo despesaTipo = new DespesaTipo(despesaTipoDto);
        return new DespesaTipoDto(repository.save(despesaTipo));
    }

    /* Remove um tipo de despesa de acordo com o id */
    public void delete(Integer id){
        DespesaTipo despesaTipo = repository.findById(id).get();
        repository.delete(despesaTipo);
    }
  
}