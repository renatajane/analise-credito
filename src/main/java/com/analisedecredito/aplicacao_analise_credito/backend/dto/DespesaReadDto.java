package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Despesa;
import com.analisedecredito.aplicacao_analise_credito.backend.model.DespesaTipo;

public class DespesaReadDto {
    
    // Propriedades
    private Integer idDespesa;
    private Cliente cliente;
    private DespesaTipo despesaTipo;
    private Double valorDespesa;

    // Construtor
    public DespesaReadDto () {
        
    }

    public DespesaReadDto (Despesa despesa) {
        BeanUtils.copyProperties(despesa, this);        
    }

    // Getters e Setters
    public Integer getIdDespesa() {
        return idDespesa;
    }
    public void setIdDespesa(Integer idDespesa) {
        this.idDespesa = idDespesa;
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
    public Double getValorDespesa() {
        return valorDespesa;
    }
    public void setValorDespesa(Double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    
}
