package com.analisedecredito.aplicacao_analise_credito.model;

import com.analisedecredito.aplicacao_analise_credito.dto.PerfilCreditoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfil_credito")
public class PerfilCredito {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_perfil_credito")
    private Integer idPerfilCredito;

    @Column(name = "nome_perfil", nullable = false)
    private String nomePerfil;

    // Construtor
    public PerfilCredito() {
        
    }

    public PerfilCredito(PerfilCreditoDto perfilCreditoDto){
        this.idPerfilCredito = perfilCreditoDto.getIdPerfilCredito();
        this.nomePerfil = perfilCreditoDto.getNomePerfil();
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
