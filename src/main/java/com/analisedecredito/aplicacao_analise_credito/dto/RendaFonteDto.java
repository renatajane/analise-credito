package com.analisedecredito.aplicacao_analise_credito.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.RendaFonte;

public class RendaFonteDto {

    // Propriedades
    private Integer idRendaFonte;
    private Integer cliente;
    private Integer rendaTipo;
    private Double valorRenda;

    // Construtor
    public RendaFonteDto() {
       
    }
    public RendaFonteDto(RendaFonte rendaFonte) {
       BeanUtils.copyProperties(rendaFonte, this);
       this.rendaTipo = rendaFonte.getRendaTipo().getIdRendaTipo();
       this.cliente = rendaFonte.getCliente().getIdCliente();
    }

    // Getters e Setters
    public Integer getIdRendaFonte() {
        return idRendaFonte;
    }

    public void setIdRendaFonte(Integer idRendaFonte) {
        this.idRendaFonte = idRendaFonte;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getRendaTipo() {
        return rendaTipo;
    }

    public void setRendaTipo(Integer rendaTipo) {
        this.rendaTipo = rendaTipo;
    }

    public Double getValorRenda() {
        return valorRenda;
    }

    public void setValorRenda(Double valorRenda) {
        this.valorRenda = valorRenda;
    }

}
