package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.IofAtual;

public class IofAtualDto {

    // Propriedades
    private Integer idIof;
    private Double taxaIof;
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

    public Date getDataCalculo() {
        return dataCalculo;
    }

    public void setDataCalculo(Date dataCalculo) {
        this.dataCalculo = dataCalculo;
    }

    public Double getTaxaIof() {
        return taxaIof;
    }

    public void setTaxaIof(Double taxaIof) {
        this.taxaIof = taxaIof;
    }

}
