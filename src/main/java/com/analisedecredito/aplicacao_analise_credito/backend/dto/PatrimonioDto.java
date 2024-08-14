package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.Patrimonio;

public class PatrimonioDto {

    // Propriedades
    private Integer idPatrimonio;
    private Integer cliente;
    private Integer patrimonioTipo;
    private Double valorPatrimonio;

    // Construtor
    public PatrimonioDto() {

    }

    public PatrimonioDto(Patrimonio patrimonio) {
        BeanUtils.copyProperties(patrimonio, this);
        this.cliente = patrimonio.getCliente().getIdCliente();
        this.patrimonioTipo = patrimonio.getPatrimonioTipo().getIdPatrimonioTipo();
    }

    // Getters e Setters
    public Integer getIdPatrimonio() {
        return idPatrimonio;
    }

    public void setIdPatrimonio(Integer idPatrimonio) {
        this.idPatrimonio = idPatrimonio;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getPatrimonioTipo() {
        return patrimonioTipo;
    }

    public void setPatrimonioTipo(Integer patrimonioTipo) {
        this.patrimonioTipo = patrimonioTipo;
    }

    public Double getValorPatrimonio() {
        return valorPatrimonio;
    }

    public void setValorPatrimonio(Double valorPatrimonio) {
        this.valorPatrimonio = valorPatrimonio;
    }


}
