package com.analisedecredito.aplicacao_analise_credito.model;

import java.util.Date;

import com.analisedecredito.aplicacao_analise_credito.dto.IofAtualDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "iof_atual")
public class IofAtual {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_iof")
    private Integer idIof;

    @Column(name = "taxa_iof", nullable = false)
    private Double taxaIof;

    @Column(name = "data_calculo", nullable = false)
    private Date dataCalculo;

    // Construtor
    public IofAtual() {

    }

    public IofAtual(IofAtualDto iofAtualDto) {
        this.idIof = iofAtualDto.getIdIof();
        this.taxaIof = iofAtualDto.getTaxaIof();
        this.dataCalculo = iofAtualDto.getDataCalculo();
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
