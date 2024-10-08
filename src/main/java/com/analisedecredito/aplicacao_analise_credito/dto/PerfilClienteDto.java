package com.analisedecredito.aplicacao_analise_credito.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.PerfilCliente;

public class PerfilClienteDto {

    // Propriedades
    private Integer idPerfilCliente;
    private String nomePerfil;
    private Integer score;
    private Double percentualRisco;

    // Construtor
    public PerfilClienteDto() {
    }

    public PerfilClienteDto(PerfilCliente perfilCliente) {
        BeanUtils.copyProperties(perfilCliente, this);
    }

    // Getters e Setters
    public Integer getIdPerfilCliente() {
        return idPerfilCliente;
    }

    public void setIdPerfilCliente(Integer idPerfilCliente) {
        this.idPerfilCliente = idPerfilCliente;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public Double getPercentualRisco() {
        return percentualRisco;
    }

    public void setPercentualRisco(Double percentualRisco) {
        this.percentualRisco = percentualRisco;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
