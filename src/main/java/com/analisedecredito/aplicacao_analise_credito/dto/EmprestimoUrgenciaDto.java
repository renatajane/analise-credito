package com.analisedecredito.aplicacao_analise_credito.dto;

public class EmprestimoUrgenciaDto {
    
    // Propriedades
    private Integer idUrgencia;
    private String prazoUrgencia;

    // Construtor
    public EmprestimoUrgenciaDto() {
        
    }

    // Getters e Setters
    public Integer getIdUrgencia() {
        return idUrgencia;
    }
    public void setIdUrgencia(Integer idUrgencia) {
        this.idUrgencia = idUrgencia;
    }
    public String getPrazoUrgencia() {
        return prazoUrgencia;
    }
    public void setPrazoUrgencia(String prazoUrgencia) {
        this.prazoUrgencia = prazoUrgencia;
    } 
}
