package com.analisedecredito.aplicacao_analise_credito.model;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoUrgenciaDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "emprestimo_urgencia")
public class EmprestimoUrgencia {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_urgencia")
    private Integer idUrgencia;

    @Column(name = "prazo_urgencia", nullable = false)
    private String prazoUrgencia;

    // Construtor
    public EmprestimoUrgencia() {
        
    }

    public EmprestimoUrgencia(EmprestimoUrgenciaDto emprestimoUrgenciaDto) {
        this.idUrgencia = emprestimoUrgenciaDto.getIdUrgencia();
        this.prazoUrgencia = emprestimoUrgenciaDto.getPrazoUrgencia();
    }

    // Getters e Setters
    public Integer getIdUrgencia() {
        return idUrgencia;
    }

    public void setIdUrgencia(Integer idUrgencia) {
        this.idUrgencia = idUrgencia;
    }

    public String getPrazoUrgencia() {
        return prazoUrgencia;
    }

    public void setPrazoUrgencia(String prazoUrgencia) {
        this.prazoUrgencia = prazoUrgencia;
    }

}