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
@Table(name="emprestimo_resultado")
public class EmprestimoResultado {
   
    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_resultado")
    private Integer idResultado;

    @ManyToOne
    @JoinColumn(name="id_requisicao_fk", referencedColumnName = "id_requisicao", nullable = false)
    private EmprestimoRequisicao emprestimoRequisicao;

    @Column(name="aprovado")
    private Boolean aprovado;

    @Column(name="descricao_resultado")
    private String descricaoResultado;

    @Column(name="data_resultado")
    private Date dataResultado;

    // Construtor
    public EmprestimoResultado() {
        
    }

    // Getters e Setters   
    public Integer getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Integer idResultado) {
        this.idResultado = idResultado;
    }

    public EmprestimoRequisicao getEmprestimoRequisicao() {
        return emprestimoRequisicao;
    }

    public void setEmprestimoRequisicao(EmprestimoRequisicao emprestimoRequisicao) {
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
