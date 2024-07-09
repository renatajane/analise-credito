package com.analisedecredito.aplicacao_analise_credito.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoModalidade;

public class EmprestimoModalidadeDto {

    // Propriedades
    private Integer idModalide;
    private String descricaoModalidade;

    // Construtor
    public EmprestimoModalidadeDto() {

    }

    public EmprestimoModalidadeDto(EmprestimoModalidade emprestimoModalidade) {
        BeanUtils.copyProperties(emprestimoModalidade, this);
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
