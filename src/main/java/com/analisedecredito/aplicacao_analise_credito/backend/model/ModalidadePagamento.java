package com.analisedecredito.aplicacao_analise_credito.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "modalidade_pagamento")
public class ModalidadePagamento {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modalidade_pagamento")
    private Integer idModalidadePagamento;

    @Column(name = "descricao_pagamento", nullable = false)
    private String descricaoPagamento;

    // Construtor
    public ModalidadePagamento() {
    }

    // Getters e Setters
    public Integer getIdModalidadePagamento() {
        return idModalidadePagamento;
    }

    public void setIdModalidadePagamento(Integer idModalidadePagamento) {
        this.idModalidadePagamento = idModalidadePagamento;
    }

    public String getDescricaoPagamento() {
        return descricaoPagamento;
    }

    public void setDescricaoPagamento(String descricaoPagamento) {
        this.descricaoPagamento = descricaoPagamento;
    }

}
