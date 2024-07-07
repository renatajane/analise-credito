package com.analisedecredito.aplicacao_analise_credito.model;

import com.analisedecredito.aplicacao_analise_credito.dto.DespesaTipoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "despesa_tipo")
public class DespesaTipo {
    
    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_despesa_tipo")
    private Integer idDespesaTipo;

    @Column(name = "descricao_despesa_tipo", nullable = false)
    private String descricaoDespesaTipo;

    // Construtor
    public DespesaTipo() {
        
    }

    public DespesaTipo(DespesaTipoDto despesaTipoDto) {
        this.idDespesaTipo = despesaTipoDto.getIdDespesaTipo();
        this.descricaoDespesaTipo = despesaTipoDto.getDescricaoDespesaTipo();
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
