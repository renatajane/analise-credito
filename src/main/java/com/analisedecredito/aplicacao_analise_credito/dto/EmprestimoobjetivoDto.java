package com.analisedecredito.aplicacao_analise_credito.dto;

public class EmprestimoObjetivoDto {

    // Propriedades
    private Integer idObjetivo;
    private String descricaoObjetivo;

    // Construtor
    public EmprestimoObjetivoDto() {
     
    }

    // Getters e Setters
    public Integer getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(Integer idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public String getDescricaoObjetivo() {
        return descricaoObjetivo;
    }

    public void setDescricaoObjetivo(String descricaoObjetivo) {
        this.descricaoObjetivo = descricaoObjetivo;
    }
}
