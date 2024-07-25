package com.analisedecredito.aplicacao_analise_credito.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "emprestimo_requisicao")
public class EmprestimoRequisicao {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requisicao")
    private Integer idRequisicao;

    @ManyToOne
    @JoinColumn(name = "id_cliente_fk", referencedColumnName = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_modalidade_fk", referencedColumnName = "id_modalidade", nullable = false)
    private EmprestimoModalidade emprestimoModalidade;

    @Column(name = "valor_requerido", nullable = false)
    private Double valorRequerido;

    @Column(name = "data_requisicao", nullable = false)
    private Date dataRequisicao;

    @Column(name = "prazo_mes", nullable = false)
    private Integer prazoMes;

    @Column(name = "valor_final", nullable = false)
    private Double valorFinal;

    @ManyToOne
    @JoinColumn(name = "id_objetivo_fk", referencedColumnName = "id_objetivo", nullable = false)
    private EmprestimoObjetivo emprestimoObjetivo;

    @ManyToOne
    @JoinColumn(name = "id_urgencia_fk", referencedColumnName = "id_urgencia", nullable = false)
    private EmprestimoUrgencia emprestimoUrgencia;

    @ManyToOne
    @JoinColumn(name = "id_iof_fk", referencedColumnName = "id_iof", nullable = false)
    private IofAtual iof;

    @ManyToOne
    @JoinColumn(name = "id_juros_fk", referencedColumnName = "id_juros", nullable = false)
    private Juros juros;

    // Construtor
    public EmprestimoRequisicao() {
    }

    // Getters e Setters
    public Juros getJuros() {
        return juros;
    }

    public void setJuros(Juros juros) {
        this.juros = juros;
    }

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
