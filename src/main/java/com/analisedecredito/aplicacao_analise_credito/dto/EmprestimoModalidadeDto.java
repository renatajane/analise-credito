package com.analisedecredito.aplicacao_analise_credito.dto;

public class EmprestimoModalidadeDto {

    // Propriedades
    private Integer idModalide;
    private String descricaoModalidade;

    // Construtor
    public EmprestimoModalidadeDto() {

    }

    // Getters e Setters
    public Integer getIdModalide() {
        return idModalide;
    }

    public void setIdModalide(Integer idModalide) {
        this.idModalide = idModalide;
    }

    public String getDescricaoModalidade() {
        return descricaoModalidade;
    }

    public void setDescricaoModalidade(String descricaoModalidade) {
        this.descricaoModalidade = descricaoModalidade;
    }

}
