package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.PatrimonioTipo;

public class PatrimonioTipoDto {

    // Propriedades
    private Integer idPatrimonioTipo;
    private String descricaoPatrimonioTipo;

    // Construtor
    public PatrimonioTipoDto() {
     
    }
    public PatrimonioTipoDto(PatrimonioTipo patrimonioTipo) {
        BeanUtils.copyProperties(patrimonioTipo, this);
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
