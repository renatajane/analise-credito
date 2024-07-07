package com.analisedecredito.aplicacao_analise_credito.dto;

import java.util.Date;

public class EmprestimoRequisicaoDto {
    
    // Propriedades
    private Integer idRequisicao;
    private Integer cliente;
    private Integer emprestimoModalidade;
    private Double valorRequerido;
    private Date dataRequisicao;
    private Integer emprestimoObjetivo;
    private Integer emprestimoUrgencia;
    private Integer iof;

    // Construtor
    public EmprestimoRequisicaoDto() {
        
    }

    // Getters e Setters
    public Integer getIdRequisicao() {
        return idRequisicao;
    }

    public void setIdRequisicao(Integer idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getEmprestimoModalidade() {
        return emprestimoModalidade;
    }

    public void setEmprestimoModalidade(Integer emprestimoModalidade) {
        this.emprestimoModalidade = emprestimoModalidade;
    }

    public Double getValorRequerido() {
        return valorRequerido;
    }

    public void setValorRequerido(Double valorRequerido) {
        this.valorRequerido = valorRequerido;
    }

    public Date getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(Date dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public Integer getEmprestimoObjetivo() {
        return emprestimoObjetivo;
    }

    public void setEmprestimoObjetivo(Integer emprestimoObjetivo) {
        this.emprestimoObjetivo = emprestimoObjetivo;
    }

    public Integer getEmprestimoUrgencia() {
        return emprestimoUrgencia;
    }

    public void setEmprestimoUrgencia(Integer emprestimoUrgencia) {
        this.emprestimoUrgencia = emprestimoUrgencia;
    }

    public Integer getIof() {
        return iof;
    }

    public void setIof(Integer iof) {
        this.iof = iof;
    }
    
}
