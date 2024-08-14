package com.analisedecredito.aplicacao_analise_credito.backend.model;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.EmprestimoObjetivoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "emprestimo_objetivo")
public class EmprestimoObjetivo {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_objetivo")
    private Integer idObjetivo;

    @Column(name = "descricao_objetivo",nullable = false)
    private String descricaoObjetivo;

    // Construtor
    public EmprestimoObjetivo() {
    }

    public EmprestimoObjetivo(EmprestimoObjetivoDto emprestimoObjetivoDto) {
        this.idObjetivo = emprestimoObjetivoDto.getIdObjetivo();
        this.descricaoObjetivo = emprestimoObjetivoDto.getDescricaoObjetivo();
    }

    // Getters e Setters
    public Integer getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(Integer idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public String getDescricaoObjetivo() {
        return descricaoObjetivo;
    }

    public void setDescricaoObjetivo(String descricaoObjetivo) {
        this.descricaoObjetivo = descricaoObjetivo;
    }

}
