package com.analisedecredito.aplicacao_analise_credito.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.RendaTipo;

public class RendaTipoDto {

    // Propriedades
    private Integer idRendaTipo;
    private String descricaoRendaTipo;

    // Construtores
    public RendaTipoDto() {

    }

    // Get e set
    public RendaTipoDto(RendaTipo rendaTipo) {
        BeanUtils.copyProperties(rendaTipo, this);
    }

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
