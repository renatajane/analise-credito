package com.analisedecredito.aplicacao_analise_credito.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.PerfilCredito;

public class PerfilCreditoDto {

    // Propriedades
    private Integer idPerfilCredito;
    private String nomePerfil;

    // Construtor
    public PerfilCreditoDto() {

    }

    public PerfilCreditoDto(PerfilCredito perfilCredito) {
        BeanUtils.copyProperties(perfilCredito, this);
    }

    // Getters e Setters
    public Integer getIdPerfilCredito() {
        return idPerfilCredito;
    }

    public void setIdPerfilCredito(Integer idPerfilCredito) {
        this.idPerfilCredito = idPerfilCredito;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

}
