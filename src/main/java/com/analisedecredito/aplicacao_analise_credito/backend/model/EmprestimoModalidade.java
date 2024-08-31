package com.analisedecredito.aplicacao_analise_credito.backend.model;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.EmprestimoModalidadeDto;

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
    private Integer idModalidade;

    @Column(name = "descricao_modalidade", nullable = false)
    private String descricaoModalidade;

    // Construtor
    public EmprestimoModalidade() {
        
    }

    public EmprestimoModalidade(EmprestimoModalidadeDto emprestimoModalidadeDto) {
        this.idModalidade = emprestimoModalidadeDto.getIdModalidade();
        this.descricaoModalidade = emprestimoModalidadeDto.getDescricaoModalidade();
    }

    // Getters e Setters
    public Integer getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(Integer idModalidade) {
        this.idModalidade = idModalidade;
    }

    public String getDescricaoModalidade() {
        return descricaoModalidade;
    }

    public void setDescricaoModalidade(String descricaoModalidade) {
        this.descricaoModalidade = descricaoModalidade;
    }
}
