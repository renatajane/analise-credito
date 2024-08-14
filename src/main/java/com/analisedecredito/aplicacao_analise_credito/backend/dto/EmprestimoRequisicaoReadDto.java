package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoModalidade;
import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoObjetivo;
import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoUrgencia;
import com.analisedecredito.aplicacao_analise_credito.backend.model.IofAtual;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Juros;

public class EmprestimoRequisicaoReadDto {

    // Propriedades
    private Integer idRequisicao;
    private Cliente cliente;
    private EmprestimoModalidade emprestimoModalidade;
    private Double valorRequerido;
    private Date dataRequisicao;
    private EmprestimoObjetivo emprestimoObjetivo;
    private EmprestimoUrgencia emprestimoUrgencia;
    private IofAtual iof;
    private Juros juros;
    private Integer prazoMes;
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
    public EmprestimoRequisicaoReadDto() {
    }

    public EmprestimoRequisicaoReadDto(EmprestimoRequisicao emprestimoRequisicao) {
        BeanUtils.copyProperties(emprestimoRequisicao, this);
        this.cliente = emprestimoRequisicao.getCliente();
        this.emprestimoModalidade = emprestimoRequisicao.getEmprestimoModalidade();
        this.emprestimoObjetivo = emprestimoRequisicao.getEmprestimoObjetivo();
        this.emprestimoUrgencia = emprestimoRequisicao.getEmprestimoUrgencia();
        this.iof = emprestimoRequisicao.getIof();
        this.juros = emprestimoRequisicao.getJuros();
    }

    // Getters e Setters
    public Integer getIdRequisicao() {
        return idRequisicao;
    }

    public void setIdRequisicao(Integer idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EmprestimoModalidade getEmprestimoModalidade() {
        return emprestimoModalidade;
    }

    public void setEmprestimoModalidade(EmprestimoModalidade emprestimoModalidade) {
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

    public EmprestimoObjetivo getEmprestimoObjetivo() {
        return emprestimoObjetivo;
    }

    public void setEmprestimoObjetivo(EmprestimoObjetivo emprestimoObjetivo) {
        this.emprestimoObjetivo = emprestimoObjetivo;
    }

    public EmprestimoUrgencia getEmprestimoUrgencia() {
        return emprestimoUrgencia;
    }

    public void setEmprestimoUrgencia(EmprestimoUrgencia emprestimoUrgencia) {
        this.emprestimoUrgencia = emprestimoUrgencia;
    }

    public IofAtual getIof() {
        return iof;
    }

    public void setIof(IofAtual iof) {
        this.iof = iof;
    }

    public Juros getJuros() {
        return juros;
    }

    public void setJuros(Juros juros) {
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
