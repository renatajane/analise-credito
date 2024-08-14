package com.analisedecredito.aplicacao_analise_credito.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "juros")
public class Juros {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_juros")
    private Integer idJuros;

    @Column(name = "taxa_juros_mensal", nullable = false)
    private Double taxaJurosMensal;

    @Column(name = "data_vigencia", nullable = false)
    private Date dataVigencia;

    // Construtor
    public Juros() {
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
