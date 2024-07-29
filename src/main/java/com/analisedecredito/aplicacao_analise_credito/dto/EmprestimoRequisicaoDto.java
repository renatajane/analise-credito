package com.analisedecredito.aplicacao_analise_credito.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;

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
    private Integer juros;
    private Integer prazoMes;
    private Double valorFinal;
    private Integer modalidadePagamento;

    // Construtor
    public EmprestimoRequisicaoDto() {

    }

    public EmprestimoRequisicaoDto(EmprestimoRequisicao emprestimoRequisicao) {
        BeanUtils.copyProperties(emprestimoRequisicao, this);
        this.cliente = emprestimoRequisicao.getCliente().getIdCliente();
        this.emprestimoModalidade = emprestimoRequisicao.getEmprestimoModalidade().getIdModalide();
        this.emprestimoObjetivo = emprestimoRequisicao.getEmprestimoObjetivo().getIdObjetivo();
        this.emprestimoUrgencia = emprestimoRequisicao.getEmprestimoObjetivo().getIdObjetivo();
        this.iof = emprestimoRequisicao.getIof().getIdIof();
        this.juros = emprestimoRequisicao.getJuros().getIdJuros();
        this.prazoMes = emprestimoRequisicao.getPrazoMes();
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

    public Integer getJuros() {
        return juros;
    }

    public void setJuros(Integer juros) {
        this.juros = juros;
    }

    public Integer getPrazoMes() {
        return prazoMes;
    }

    public void setPrazoMes(Integer prazoMes) {
        this.prazoMes = prazoMes;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Integer getModalidadePagamento() {
        return modalidadePagamento;
    }

    public void setModalidadePagamento(Integer modalidadePagamento) {
        this.modalidadePagamento = modalidadePagamento;
    }

}
