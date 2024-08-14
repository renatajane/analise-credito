package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoObjetivo;

public class EmprestimoObjetivoDto {

    // Propriedades
    private Integer idObjetivo;
    private String descricaoObjetivo;

    // Construtor
    public EmprestimoObjetivoDto() {

    }

    public EmprestimoObjetivoDto(EmprestimoObjetivo emprestimoObjetivo) {
        BeanUtils.copyProperties(emprestimoObjetivo, this);
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
