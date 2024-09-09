package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.PatrimonioTipoDto;
import com.analisedecredito.aplicacao_analise_credito.model.PatrimonioTipo;
import com.analisedecredito.aplicacao_analise_credito.repository.PatrimonioTipoRepository;

@Service
public class PatrimonioTipoService {
    
    @Autowired
    PatrimonioTipoRepository repository;

    /* Retorna um tipo de patrimônio de acordo com o id */
    public PatrimonioTipoDto findById(Integer id) {
        return new PatrimonioTipoDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todos os tipos de patrimônios cadastrados */
    public List<PatrimonioTipoDto> list() {
        List<PatrimonioTipo> patrimonioTipos = repository.findAll();
        return patrimonioTipos.stream().map(PatrimonioTipoDto::new).toList();
    }

    /* Cria um novo tipo de patrimônio com base nos dados fornecidos */
    public void create(PatrimonioTipoDto patrimonioTipoDto) {
        PatrimonioTipo patrimonioTipo = new PatrimonioTipo(patrimonioTipoDto);
        repository.save(patrimonioTipo);
    }

    /* Atualiza os dados de um tipo de patrimônio existente com base nos dados fornecidos */
    public PatrimonioTipoDto update(PatrimonioTipoDto patrimonioTipoDto) {
        PatrimonioTipo patrimonioTipo = new PatrimonioTipo(patrimonioTipoDto);
        return new PatrimonioTipoDto(repository.save(patrimonioTipo));
    }

    /* Remove um tipo de patrimônio de acordo com o id */
    public void delete(Integer id) {
        PatrimonioTipo patrimonioTipo = repository.findById(id).get();
        repository.delete(patrimonioTipo);
    }
}
