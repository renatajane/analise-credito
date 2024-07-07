package com.analisedecredito.aplicacao_analise_credito.model;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoModalidadeDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "emprestimo_modalidade")
public class EmprestimoModalidade {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_modalidade")
    private Integer idModalide;

    @Column(name = "descricao_modalidade", nullable = false)
    private String descricaoModalidade;

    // Construtor
    public EmprestimoModalidade() {
        super();
    }

    public EmprestimoModalidade(EmprestimoModalidadeDto emprestimoModalidadeDto) {
        this.idModalide = emprestimoModalidadeDto.getIdModalide();
        this.descricaoModalidade = emprestimoModalidadeDto.getDescricaoModalidade();
    }

    // Getters e Setters
    public Integer getIdModalide() {
        return idModalide;
    }

    public void setIdModalide(Integer idModalide) {
        this.idModalide = idModalide;
    }

    public String getDescricaoModalidade() {
        return descricaoModalidade;
    }

    public void setDescricaoModalidade(String descricaoModalidade) {
        this.descricaoModalidade = descricaoModalidade;
    }
}
