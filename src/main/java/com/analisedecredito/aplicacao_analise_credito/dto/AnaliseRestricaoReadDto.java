package com.analisedecredito.aplicacao_analise_credito.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.AnaliseRestricao;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;

public class AnaliseRestricaoReadDto {

    // Propriedades
    private Integer idRestricao;
    private Cliente cliente;

    // Construtor
    public AnaliseRestricaoReadDto() {
    }

    public AnaliseRestricaoReadDto(AnaliseRestricao analiseRestricao) {
        BeanUtils.copyProperties(analiseRestricao, this);
    }

    // Getters e Setters
    public Integer getIdRestricao() {
        return idRestricao;
    }

    public void setIdRestricao(Integer idRestricao) {
        this.idRestricao = idRestricao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
