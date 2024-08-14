package com.analisedecredito.aplicacao_analise_credito.backend.dto;

public class ModalidadePagamentoDto {

    // Propriedades
    private Integer idModalidadePagamento;
    private String descricaoPagamento;

    // Construtor
    public ModalidadePagamentoDto() {
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
