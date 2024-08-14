package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.DespesaTipo;

public class DespesaTipoDto {

    // Propriedades
    private Integer idDespesaTipo;
    private String descricaoDespesaTipo;

    // Construtor
    public DespesaTipoDto() {
      
    }

     public DespesaTipoDto(DespesaTipo despesaTipo) {
        BeanUtils.copyProperties(despesaTipo, this);
    }

    // Getters e Setters
    public Integer getIdDespesaTipo() {
        return idDespesaTipo;
    }

    public void setIdDespesaTipo(Integer idDespesaTipo) {
        this.idDespesaTipo = idDespesaTipo;
    }

    public String getDescricaoDespesaTipo() {
        return descricaoDespesaTipo;
    }

    public void setDescricaoDespesaTipo(String descricaoDespesaTipo) {
        this.descricaoDespesaTipo = descricaoDespesaTipo;
    }
}
