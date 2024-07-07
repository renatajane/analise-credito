package com.analisedecredito.aplicacao_analise_credito.dto;

public class DespesaTipoDto {

    // Propriedades
    private Integer idDespesaTipo;
    private String descricaoDespesaTipo;

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
