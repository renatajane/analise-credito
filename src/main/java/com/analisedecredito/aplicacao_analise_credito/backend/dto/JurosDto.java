package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import java.util.Date;

public class JurosDto {

    // Propriedades
    private Integer idJuros;
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
