package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoRequisicao;

public class EmprestimoRequisicaoDto {

    // Propriedades
    private Integer idRequisicao;
    private Integer cliente;
    private Integer emprestimoModalidade;
    private Integer prazoMes;
    private Double valorRequerido;
    private Date dataRequisicao;
    private Integer emprestimoObjetivo;
    private Integer emprestimoUrgencia;
    private Integer iof;
    private Integer juros;
    private Integer modalidadePagamento;
    private Integer diaPagamento;
    private Boolean aprovado;
    private String descricaoResultado;
    private Date dataResultado;
    private Double jurosCalculado;
    private Double iofCalculado;
    private Double valorParcela;
    private Double valorTotal;

    // Construtor
    public EmprestimoRequisicaoDto() {
    }

    public EmprestimoRequisicaoDto(EmprestimoRequisicao emprestimoRequisicao) {
        BeanUtils.copyProperties(emprestimoRequisicao, this);
        this.cliente = emprestimoRequisicao.getCliente().getIdCliente();
        this.emprestimoModalidade = emprestimoRequisicao.getEmprestimoModalidade().getIdModalide();
        this.emprestimoObjetivo = emprestimoRequisicao.getEmprestimoObjetivo().getIdObjetivo();
        this.emprestimoUrgencia = emprestimoRequisicao.getEmprestimoUrgencia().getIdUrgencia();
        this.iof = emprestimoRequisicao.getIof().getIdIof();
        this.juros = emprestimoRequisicao.getJuros().getIdJuros();
        this.prazoMes = emprestimoRequisicao.getPrazoMes();
        this.modalidadePagamento = emprestimoRequisicao.getModalidadePagamento().getIdModalidadePagamento();
        this.diaPagamento = emprestimoRequisicao.getDiaPagamento();
        this.aprovado = emprestimoRequisicao.getAprovado();
        this.descricaoResultado = emprestimoRequisicao.getDescricaoResultado();
        this.dataResultado = emprestimoRequisicao.getDataResultado();
        this.jurosCalculado = emprestimoRequisicao.getJurosCalculado();
        this.iofCalculado = emprestimoRequisicao.getIofCalculado();
        this.valorParcela = emprestimoRequisicao.getValorParcela();
        this.valorTotal = emprestimoRequisicao.getValorTotal();
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

    public Integer getModalidadePagamento() {
        return modalidadePagamento;
    }

    public void setModalidadePagamento(Integer modalidadePagamento) {
        this.modalidadePagamento = modalidadePagamento;
    }

    public Integer getDiaPagamento() {
        return diaPagamento;
    }

    public void setDiaPagamento(Integer diaPagamento) {
        this.diaPagamento = diaPagamento;
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

    public Double getJurosCalculado() {
        return jurosCalculado;
    }

    public void setJurosCalculado(Double jurosCalculado) {
        this.jurosCalculado = jurosCalculado;
    }

    public Double getIofCalculado() {
        return iofCalculado;
    }

    public void setIofCalculado(Double iofCalculado) {
        this.iofCalculado = iofCalculado;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
