package com.analisedecredito.aplicacao_analise_credito.model;

import java.util.Date;

import com.analisedecredito.aplicacao_analise_credito.enums.Identificador;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "analise_credito")
public class AnaliseCredito {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_analise_credito")
    private Integer idAnaliseCredito;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "identificador", nullable = false)
    private Identificador identificador;

    @Column(name = "aspecto", nullable = false)
    private String aspecto;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "vigente", nullable = false)
    private Boolean vigente;

    @Column(name = "data_validade", nullable = false)
    private Date dataValidade;

    // Construtor
    public AnaliseCredito() {
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

    public Identificador getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Identificador identificador) {
        this.identificador = identificador;
    }

}