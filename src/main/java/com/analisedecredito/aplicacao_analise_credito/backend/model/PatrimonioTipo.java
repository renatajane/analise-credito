package com.analisedecredito.aplicacao_analise_credito.backend.model;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.PatrimonioTipoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "patrimonio_tipo")
public class PatrimonioTipo {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_patrimonio_tipo")
    private Integer idPatrimonioTipo;

    @Column(name = "descricao_patrimonio_tipo", nullable = false)
    private String descricaoPatrimonioTipo;

    // Construtor
    public PatrimonioTipo() {
        
    }
    
    public PatrimonioTipo(PatrimonioTipoDto patrimonioTipoDto) {
        this.idPatrimonioTipo = patrimonioTipoDto.getIdPatrimonioTipo();
        this.descricaoPatrimonioTipo = patrimonioTipoDto.getDescricaoPatrimonioTipo();
    }

    // Getters e Setters
    public Integer getIdPatrimonioTipo() {
        return idPatrimonioTipo;
    }

    public void setIdPatrimonioTipo(Integer idPatrimonioTipo) {
        this.idPatrimonioTipo = idPatrimonioTipo;
    }

    public String getDescricaoPatrimonioTipo() {
        return descricaoPatrimonioTipo;
    }

    public void setDescricaoPatrimonioTipo(String descricaoPatrimonioTipo) {
        this.descricaoPatrimonioTipo = descricaoPatrimonioTipo;
    }

}
