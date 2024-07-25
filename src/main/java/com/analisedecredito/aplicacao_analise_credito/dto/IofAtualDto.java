package com.analisedecredito.aplicacao_analise_credito.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.IofAtual;

public class IofAtualDto {

    // Propriedades
    private Integer idIof;
    private Double iofDiario;
    private Double iofTotal;
    private Date dataCalculo;

    // Construtor
    public IofAtualDto() {

    }

    public IofAtualDto(IofAtual iofAtual) {
        BeanUtils.copyProperties(iofAtual, this);
    }

    // Getters e Setters
    public Integer getIdIof() {
        return idIof;
    }

    public void setIdIof(Integer idIof) {
        this.idIof = idIof;
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
