package com.analisedecredito.aplicacao_analise_credito.dto;

public class PatrimonioTipoDto {

    // Propriedades
    private Integer idPatrimonioTipo;
    private String descricaoPatrimonioTipo;

    // Construtor
    public PatrimonioTipoDto() {
     
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
