package com.analisedecredito.aplicacao_analise_credito.dto;

import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.PatrimonioTipo;

public class PatrimonioDto {

    // Propriedades
    private Integer idPatrimonio;
    private Cliente cliente;
    private PatrimonioTipo patrimonioTipo;
    private Double valorPatrimonio;

    // Construtor
    public PatrimonioDto() {

    }

    // Getters e Setters
    public Integer getIdPatrimonio() {
        return idPatrimonio;
    }

    public void setIdPatrimonio(Integer idPatrimonio) {
        this.idPatrimonio = idPatrimonio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PatrimonioTipo getPatrimonioTipo() {
        return patrimonioTipo;
    }

    public void setPatrimonioTipo(PatrimonioTipo patrimonioTipo) {
        this.patrimonioTipo = patrimonioTipo;
    }

    public Double getValorPatrimonio() {
        return valorPatrimonio;
    }

    public void setValorPatrimonio(Double valorPatrimonio) {
        this.valorPatrimonio = valorPatrimonio;
    }


}
