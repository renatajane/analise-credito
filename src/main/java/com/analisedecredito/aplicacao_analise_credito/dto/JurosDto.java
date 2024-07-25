package com.analisedecredito.aplicacao_analise_credito.dto;

import java.util.Date;

public class JurosDto {

    // Propriedades
    private Integer idJuros;
    private String nomeModalidade;
    private Double taxaJurosAnual;
    private Double taxaJurosMensal;
    private Date dataVigencia;

    // Construtor
    public JurosDto() {
    }

    // Getters e Setters
    public Integer getIdJuros() {
        return idJuros;
    }
    public void setIdJuros(Integer idJuros) {
        this.idJuros = idJuros;
    }
    public String getNomeModalidade() {
        return nomeModalidade;
    }
    public void setNomeModalidade(String nomeModalidade) {
        this.nomeModalidade = nomeModalidade;
    }
    public Double getTaxaJurosAnual() {
        return taxaJurosAnual;
    }
    public void setTaxaJurosAnual(Double taxaJurosAnual) {
        this.taxaJurosAnual = taxaJurosAnual;
    }
    public Double getTaxaJurosMensal() {
        return taxaJurosMensal;
    }
    public void setTaxaJurosMensal(Double taxaJurosMensal) {
        this.taxaJurosMensal = taxaJurosMensal;
    }
    public Date getDataVigencia() {
        return dataVigencia;
    }
    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
    }

    
}
