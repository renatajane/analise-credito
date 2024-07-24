package com.analisedecredito.aplicacao_analise_credito.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoResultado;

public class EmprestimoResultadoDto {

    // Propriedades
    private Integer idResultado;
    private Integer emprestimoRequisicao;
    private Boolean aprovado;
    private String descricaoResultado;
    private Date dataResultado;

    // Construtor
    public EmprestimoResultadoDto() {
    }

    public EmprestimoResultadoDto(EmprestimoResultado emprestimoResultado) {
        BeanUtils.copyProperties(emprestimoResultado, this);
        this.emprestimoRequisicao = emprestimoResultado.getEmprestimoRequisicao().getIdRequisicao();
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

    public Date getDataResultado() {
        return dataResultado;
    }

    public void setDataResultado(Date dataResultado) {
        this.dataResultado = dataResultado;
    }    
}
