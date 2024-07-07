package com.analisedecredito.aplicacao_analise_credito.dto;

public class DespesaDto {

    // Propriedades
    private Integer idDespesa;
    private Integer cliente;
    private Integer despesaTipo;
    private Double valorDespesa;
    
    // Construtor
    public DespesaDto() {
        
    }

    public Integer getIdDespesa() {
        return idDespesa;
    }

    public void setIdDespesa(Integer idDespesa) {
        this.idDespesa = idDespesa;
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

    public Double getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(Double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    
}
