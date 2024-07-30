package com.analisedecredito.aplicacao_analise_credito.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoParcela;

public class EmprestimoParcelaDto {

    // Propriedades
    private Integer idEmprestimoParcela;
    private Date dataVencimento;
    private Double valorParcela;

    // Construtor
    public EmprestimoParcelaDto() {
    }

    public EmprestimoParcelaDto(EmprestimoParcela emprestimoParcela) {
        BeanUtils.copyProperties(emprestimoParcela, this);
    }

    // Getters e Setters
    public Integer getIdEmprestimoParcela() {
        return idEmprestimoParcela;
    }

    public void setIdEmprestimoParcela(Integer idEmprestimoParcela) {
        this.idEmprestimoParcela = idEmprestimoParcela;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

}