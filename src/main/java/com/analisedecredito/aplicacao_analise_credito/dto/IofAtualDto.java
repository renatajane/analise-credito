package com.analisedecredito.aplicacao_analise_credito.dto;

import java.util.Date;

public class IofAtualDto {
    
    // Propriedades
    private Integer idIof;
    private Double iofDiarioMaiorPrazo;
    private Double iofDiario;
    private Double iofTotal;
    private Date dataCalculo;

    // Construtor
    public IofAtualDto() {
        
    }

    // Getters e Setters
    public Integer getIdIof() {
        return idIof;
    }

    public void setIdIof(Integer idIof) {
        this.idIof = idIof;
    }

    public Double getIofDiarioMaiorPrazo() {
        return iofDiarioMaiorPrazo;
    }

    public void setIofDiarioMaiorPrazo(Double iofDiarioMaiorPrazo) {
        this.iofDiarioMaiorPrazo = iofDiarioMaiorPrazo;
    }

    public Double getIofDiario() {
        return iofDiario;
    }

    public void setIofDiario(Double iofDiario) {
        this.iofDiario = iofDiario;
    }

    public Double getIofTotal() {
        return iofTotal;
    }

    public void setIofTotal(Double iofTotal) {
        this.iofTotal = iofTotal;
    }

    public Date getDataCalculo() {
        return dataCalculo;
    }

    public void setDataCalculo(Date dataCalculo) {
        this.dataCalculo = dataCalculo;
    }

    
}
