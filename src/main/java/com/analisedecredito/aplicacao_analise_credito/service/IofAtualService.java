package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.IofAtualDto;
import com.analisedecredito.aplicacao_analise_credito.model.IofAtual;
import com.analisedecredito.aplicacao_analise_credito.repository.IofAtualRepository;

@Service
public class IofAtualService {

    @Autowired
    IofAtualRepository repository;

    /* Retorna iof de acordo com o id */
    public IofAtualDto findById(Integer id) {
        return new IofAtualDto(repository.findById(id).get());
    }

    /* Retorna uma lista de iofs cadastrados */
    public List<IofAtualDto> list() {
        List<IofAtual> listaIof = repository.findAll();
        return listaIof.stream().map(IofAtualDto::new).toList();
    }

    /* Cria um novo iof com base nos dados fornecidos */
    public void create(IofAtualDto iofAtualDto) {
        IofAtual iofAtual = new IofAtual(iofAtualDto);
        repository.save(iofAtual);
    }

    /* Atualiza os dados de iof existente */
    public IofAtualDto update(IofAtualDto iofAtualDto) {
        IofAtual iofAtual = new IofAtual(iofAtualDto);
        return new IofAtualDto(repository.save(iofAtual));
    }
    
    /* Remove um dado de iof pelo id */
    public void delete(Integer id) {
        IofAtual iofAtual = repository.findById(id).get();
        repository.delete(iofAtual);
    }

}
