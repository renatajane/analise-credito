package com.analisedecredito.aplicacao_analise_credito.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.Despesa;
import com.analisedecredito.aplicacao_analise_credito.model.DespesaTipo;

public class DespesaDto {

    // Propriedades
    private Integer idDespesa;
    private Cliente cliente;
    private DespesaTipo despesaTipo;
    private Double valorDespesa;

    // Construtor
    public DespesaDto() {

    }

    public DespesaDto(Despesa despesa, Cliente cliente, DespesaTipo despesaTipo) {
        BeanUtils.copyProperties(despesa, this);
        this.cliente = cliente;
        this.despesaTipo = despesaTipo;
    }

    // Getters e Setters
    public Integer getIdDespesa() {
        return idDespesa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public DespesaTipo getDespesaTipo() {
        return despesaTipo;
    }

    public void setDespesaTipo(DespesaTipo despesaTipo) {
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
}
