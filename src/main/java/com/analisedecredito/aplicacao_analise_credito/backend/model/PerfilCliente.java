package com.analisedecredito.aplicacao_analise_credito.backend.model;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.PerfilClienteDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfil_cliente")
public class PerfilCliente {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil_cliente")
    private Integer idPerfilCliente;

    @Column(name = "nome_perfil", nullable = false)
    private String nomePerfil;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "percentual_risco", nullable = false)
    private Double percentualRisco;

    // Construtores
    public PerfilCliente() {
    }

    public PerfilCliente(PerfilClienteDto perfilCliente) {
        this.nomePerfil = perfilCliente.getNomePerfil();
        this.score = perfilCliente.getScore();
        this.percentualRisco = perfilCliente.getPercentualRisco();
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
