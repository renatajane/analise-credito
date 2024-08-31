package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.ModalidadePagamento;

public class ModalidadePagamentoDto {

    // Propriedades
    private Integer idModalidadePagamento;
    private String descricaoPagamento;

    // Construtor
    public ModalidadePagamentoDto() {
    }

    public ModalidadePagamentoDto(ModalidadePagamento modalidadePagamento) {
        BeanUtils.copyProperties(modalidadePagamento, this);
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
