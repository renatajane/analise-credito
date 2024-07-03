package com.analisedecredito.aplicacao_analise_credito.dto;

import com.analisedecredito.aplicacao_analise_credito.model.RendaTipo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RendaTipoDto {
    
    //Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRendaTipoDto;
    
    private String descricaoRendaTipoDto;

    //get e set
    public Integer getIdRendaTipoDto() {
        return idRendaTipoDto;
    }

    public void setIdRendaTipoDto(Integer idRendaTipoDto) {
        this.idRendaTipoDto = idRendaTipoDto;
    }

    public String getDescricaoRendaTipoDto() {
        return descricaoRendaTipoDto;
    }

    public void setDescricaoRendaTipoDto(String descricaoRendaTipoDto) {
        this.descricaoRendaTipoDto = descricaoRendaTipoDto;
    }    

    //Converte para objeto
    public RendaTipo transformaParaObjeto(){
        return new RendaTipo(idRendaTipoDto, descricaoRendaTipoDto);
    }

}
