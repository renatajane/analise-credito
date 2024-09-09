package com.analisedecredito.aplicacao_analise_credito.model;

import com.analisedecredito.aplicacao_analise_credito.dto.RendaTipoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "renda_tipo")
public class RendaTipo {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_renda_tipo")
    private Integer idRendaTipo;
    
    @Column(name = "descricao_renda_tipo", nullable = false)
    private String descricaoRendaTipo;

    // Construtor
    public RendaTipo() {
    }

    public RendaTipo(RendaTipoDto rendaTipoDto) {
        this.idRendaTipo = rendaTipoDto.getIdRendaTipo();
        this.descricaoRendaTipo = rendaTipoDto.getDescricaoRendaTipo();
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
