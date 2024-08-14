package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.Despesa;

public class DespesaDto {

    // Propriedades
    private Integer idDespesa;
    private Integer cliente;
    private Integer despesaTipo;
    private Double valorDespesa;
    private Double despesaTotal; 

    // Construtor
    public DespesaDto() {

    }

    public DespesaDto(Despesa despesa) {
        BeanUtils.copyProperties(despesa, this);
        this.cliente = despesa.getCliente().getIdCliente();
        this.despesaTipo = despesa.getDespesaTipo().getIdDespesaTipo();
    }

    // Getters e Setters
    public Integer getIdDespesa() {
        return idDespesa;
    }
    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getDespesaTipo() {
        return despesaTipo;
    }

    public void setDespesaTipo(Integer despesaTipo) {
        this.despesaTipo = despesaTipo;
    }

    public void setIdDespesa(Integer idDespesa) {
        this.idDespesa = idDespesa;
    }

    public Double getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(Double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public Double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(Double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }
}
