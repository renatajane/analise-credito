package com.analisedecredito.aplicacao_analise_credito.model;

import com.analisedecredito.aplicacao_analise_credito.dto.RendaTipoDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RendaTipo {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRendaTipo;

    private String descricaoRendaTipo;

    // Construtor
    public RendaTipo() {
    }

    public RendaTipo(RendaTipoDto rendaTipoDto) {
        this.idRendaTipo = rendaTipoDto.getIdRendaTipo();
        this.descricaoRendaTipo = rendaTipoDto.getDescricaoRendaTipo();
    }

    // Get e set
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
