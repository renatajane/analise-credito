package com.analisedecredito.aplicacao_analise_credito.dto;

public class EmprestimoResultadoDto {

    // Propriedades
    private Integer idResultado;
    private Integer emprestimoRequisicao;
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

    public Integer getEmprestimoRequisicao() {
        return emprestimoRequisicao;
    }

    public void setEmprestimoRequisicao(Integer emprestimoRequisicao) {
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
