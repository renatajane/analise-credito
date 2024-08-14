package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.RendaTipo;

public class RendaTipoDto {

    // Propriedades
    private Integer idRendaTipo;
    private String descricaoRendaTipo;

    // Construtores
    public RendaTipoDto() {

    }
    public RendaTipoDto(RendaTipo rendaTipo) {
        BeanUtils.copyProperties(rendaTipo, this);
    }

    // Getters e Setters
    public Integer getIdRendaTipo() {
        return idRendaTipo;
    }

    public void setIdRendaTipo(Integer idRendaTipo) {
        this.idRendaTipo = idRendaTipo;
    }

    public String getDescricaoRendaTipo() {
        return descricaoRendaTipo;
    }

    public void setDescricaoRendaTipo(String descricaoRendaTipo) {
        this.descricaoRendaTipo = descricaoRendaTipo;
    }

}
