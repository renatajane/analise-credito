package com.analisedecredito.aplicacao_analise_credito.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoModalidade;

public class EmprestimoModalidadeDto {

    // Propriedades
    private Integer idModalidade;
    private String descricaoModalidade;

    // Construtor
    public EmprestimoModalidadeDto() {

    }

    public EmprestimoModalidadeDto(EmprestimoModalidade emprestimoModalidade) {
        BeanUtils.copyProperties(emprestimoModalidade, this);
    }

    // Getters e Setters
    public String getDescricaoModalidade() {
        return descricaoModalidade;
    }

    public void setDescricaoModalidade(String descricaoModalidade) {
        this.descricaoModalidade = descricaoModalidade;
    }

    public Integer getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(Integer idModalidade) {
        this.idModalidade = idModalidade;
    }

}
