package com.analisedecredito.aplicacao_analise_credito.dto;

import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;

public class EmprestimoResultadoDto {

    // Propriedades
    private Integer idResultado;
    private EmprestimoRequisicao emprestimoRequisicao;
    private Boolean aprovado;
    private String descricaoResultado;

    // Construtor
    public EmprestimoResultadoDto() {
        
    }

    // Getters e Setters
    public Integer getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Integer idResultado) {
        this.idResultado = idResultado;
    }

    public EmprestimoRequisicao getEmprestimoRequisicao() {
        return emprestimoRequisicao;
    }

    public void setEmprestimoRequisicao(EmprestimoRequisicao emprestimoRequisicao) {
        this.emprestimoRequisicao = emprestimoRequisicao;
    }

    public Boolean getAprovado() {
        return aprovado;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public String getDescricaoResultado() {
        return descricaoResultado;
    }

    public void setDescricaoResultado(String descricaoResultado) {
        this.descricaoResultado = descricaoResultado;
    }   

}
