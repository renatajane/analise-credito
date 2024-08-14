package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import com.analisedecredito.aplicacao_analise_credito.backend.model.RendaFonte;

public class PerfilClienteReadDto {

    // Propriedades
    private Integer idPerfilCliente;
    private String nomePerfil;
    private Double score;
    private Double percentualRisco;
    private RendaFonte rendaFonte;

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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getPercentualRisco() {
        return percentualRisco;
    }

    public void setPercentualRisco(Double percentualRisco) {
        this.percentualRisco = percentualRisco;
    }

    public RendaFonte getRendaFonte() {
        return rendaFonte;
    }

    public void setRendaFonte(RendaFonte rendaFonte) {
        this.rendaFonte = rendaFonte;
    }

}
