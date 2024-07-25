package com.analisedecredito.aplicacao_analise_credito.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoModalidade;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoObjetivo;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoUrgencia;
import com.analisedecredito.aplicacao_analise_credito.model.IofAtual;
import com.analisedecredito.aplicacao_analise_credito.model.Juros;

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
    private Double valorFinal;

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

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }
}
