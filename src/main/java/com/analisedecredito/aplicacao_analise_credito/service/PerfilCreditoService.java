package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.PerfilCreditoDto;
import com.analisedecredito.aplicacao_analise_credito.model.PerfilCredito;
import com.analisedecredito.aplicacao_analise_credito.repository.PerfilCreditoRepository;

@Service
public class PerfilCreditoService {

    @Autowired
    PerfilCreditoRepository repository;

    /* Retorna um perfil de crédito de acordo com o id */
    public PerfilCreditoDto findById(Integer id) {
        return new PerfilCreditoDto(repository.findById(id).get());
    }

    /* Retorna uma lista de perfis de créditos cadastrados */
    public List<PerfilCreditoDto> list() {
        List<PerfilCredito> listPerfilCredito = repository.findAll();
        return listPerfilCredito.stream().map(PerfilCreditoDto::new).toList();
    }

    /* Cria um novo perfil de crédito com base nos dados fornecidos */
    public void create(PerfilCreditoDto perfilCreditoDto) {
        PerfilCredito perfilCredito = new PerfilCredito(perfilCreditoDto);
        repository.save(perfilCredito);
    }

    /*
     * Atualiza os dados de um perfil de crédito existente com base nos dados
     * fornecidos
     */
    public PerfilCreditoDto update(PerfilCreditoDto perfilCreditoDto) {
        PerfilCredito perfilCredito = new PerfilCredito(perfilCreditoDto);
        return new PerfilCreditoDto(repository.save(perfilCredito));
    }

    /* Remove um tipo de perfil de crédito de acordo com o id */
    public void delete(Integer id) {
        PerfilCredito perfilCredito = repository.findById(id).get();
        repository.delete(perfilCredito);
    }
}
