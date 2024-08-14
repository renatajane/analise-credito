package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import java.util.Date;

public class AnaliseCreditoDto {
    
    // Propriedades
    private Integer idAnaliseCredito;
    private String aspecto;
    private Double valor;
    private Boolean vigente;
    private Date dataValidade;
    
    // Construtor
    public AnaliseCreditoDto() {
    }

    // Getters e Setters
    public Integer getIdAnaliseCredito() {
        return idAnaliseCredito;
    }

    public void setIdAnaliseCredito(Integer idAnaliseCredito) {
        this.idAnaliseCredito = idAnaliseCredito;
    }

    public String getAspecto() {
        return aspecto;
    }

    public void setAspecto(String aspecto) {
        this.aspecto = aspecto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }  
    
}