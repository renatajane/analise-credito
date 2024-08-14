package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Patrimonio;
import com.analisedecredito.aplicacao_analise_credito.backend.model.PatrimonioTipo;

public class PatrimonioReadDto {

    // Propriedades
    private Integer idPatrimonio;
    private Cliente cliente;
    private PatrimonioTipo patrimonioTipo;
    private Double valorPatrimonio;

    // Construtor
    public PatrimonioReadDto() {
    }

    public PatrimonioReadDto(Patrimonio patrimonio) {
        BeanUtils.copyProperties(patrimonio, this);
        this.cliente = patrimonio.getCliente();
        this.patrimonioTipo = patrimonio.getPatrimonioTipo();
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
