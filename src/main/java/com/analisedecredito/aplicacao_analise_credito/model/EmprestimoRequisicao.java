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
@Table(name="emprestimo_requisicao")
public class EmprestimoRequisicao {
    
    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_requisicao")
    private Integer idRequisicao;

    @ManyToOne
    @JoinColumn(name="id_cliente_fk", referencedColumnName = "id_cliente", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name="id_modalidade_fk", referencedColumnName = "id_modalidade", nullable = false)
    private EmprestimoModalidade emprestimoModalidade;

    @Column(name="valor_requerido", nullable = false)
    private Double valorRequerido;

    @Column(name="data_requisicao", nullable = false)
    private Date dataRequisicao;

    @ManyToOne
    @JoinColumn(name="id_objetivo_fk", referencedColumnName = "id_objetivo", nullable = false)
    private EmprestimoObjetivo emprestimoObjetivo;

    @ManyToOne
    @JoinColumn(name="id_urgencia_fk", referencedColumnName = "id_urgencia", nullable = false)
    private EmprestimoUrgencia emprestimoUrgencia;

    @ManyToOne
    @JoinColumn(name="id_iof_fk", referencedColumnName = "id_iof", nullable = false)
    private IofAtual iof;



}
