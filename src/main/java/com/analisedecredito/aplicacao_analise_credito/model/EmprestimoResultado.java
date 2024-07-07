package com.analisedecredito.aplicacao_analise_credito.model;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoDto;

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

    // Construtor
    public EmprestimoResultado() {
        
    }

    public EmprestimoResultado(EmprestimoResultadoDto emprestimoResultadoDto, 
    EmprestimoRequisicao emprestimoRequisicao) {
        this.idResultado = emprestimoResultadoDto.getIdResultado();
        this.emprestimoRequisicao = emprestimoRequisicao;
        this.aprovado = emprestimoResultadoDto.getAprovado();
        this.descricaoResultado = emprestimoResultadoDto.getDescricaoResultado();
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

    
}
