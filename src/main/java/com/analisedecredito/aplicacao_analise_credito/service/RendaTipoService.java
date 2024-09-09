package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.RendaTipoDto;
import com.analisedecredito.aplicacao_analise_credito.model.RendaTipo;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaTipoRepository;

@Service
public class RendaTipoService {

    @Autowired
    RendaTipoRepository repository;

    /* Retorna um tipo de renda de acordo com o id */
    public RendaTipoDto findById(Integer id) {
        return new RendaTipoDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todos os tipos de renda cadastrados */
    public List<RendaTipoDto> list() {
        List<RendaTipo> rendaTipos = repository.findAll();
        return rendaTipos.stream().map(RendaTipoDto::new).toList();
    }

    /* Cria um novo tipo de renda com base nos dados fornecidos */
    public void create(RendaTipoDto rendaTipoDto) {
        RendaTipo rendaTipo = new RendaTipo(rendaTipoDto);
        repository.save(rendaTipo);
    }

    /* Atualiza os dados de um tipo de renda existente com base nos dados fornecidos */
    public RendaTipoDto update(RendaTipoDto rendaTipoDto) {
        RendaTipo rendaTipo = new RendaTipo(rendaTipoDto);
        return new RendaTipoDto(repository.save(rendaTipo));
    }

    /* Remove um tipo de renda de acordo com o id */
    public void delete(Integer id) {
        RendaTipo rendaTipo = repository.findById(id).get();
        repository.delete(rendaTipo);
    }
}
