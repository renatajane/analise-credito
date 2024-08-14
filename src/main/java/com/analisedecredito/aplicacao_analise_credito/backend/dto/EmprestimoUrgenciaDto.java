package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoUrgencia;

public class EmprestimoUrgenciaDto {

    // Propriedades
    private Integer idUrgencia;
    private String prazoUrgencia;

    // Construtor
    public EmprestimoUrgenciaDto() {

    }

    public EmprestimoUrgenciaDto(EmprestimoUrgencia EmprestimoUrgencia) {
        BeanUtils.copyProperties(EmprestimoUrgencia, this);
    }

    // Getters e Setters
    public Integer getIdUrgencia() {
        return idUrgencia;
    }

    public void setIdUrgencia(Integer idUrgencia) {
        this.idUrgencia = idUrgencia;
    }

    public String getPrazoUrgencia() {
        return prazoUrgencia;
    }

    public void setPrazoUrgencia(String prazoUrgencia) {
        this.prazoUrgencia = prazoUrgencia;
    }
}
