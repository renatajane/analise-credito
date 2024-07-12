package com.analisedecredito.aplicacao_analise_credito.model;

import com.analisedecredito.aplicacao_analise_credito.dto.DespesaDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="despesa")
public class Despesa {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_despesa")
    private Integer idDespesa;

    @ManyToOne
    @JoinColumn(name = "id_cliente_fk", referencedColumnName = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_despesa_tipo_fk", referencedColumnName = "id_despesa_tipo", nullable = false)
    private DespesaTipo despesaTipo;

    @Column(name="valor_despesa")
    private Double valorDespesa;

    // Construtor
    public Despesa() {
        
    }

    public Despesa(DespesaDto despesaDto, Cliente cliente, DespesaTipo despesaTipo) {
        this.idDespesa = despesaDto.getIdDespesa();
        this.cliente = cliente;
        this.despesaTipo = despesaTipo;
        this.valorDespesa = despesaDto.getValorDespesa();
    }

    // Getters e Setters
    public Integer getIdDespesa() {
        return idDespesa;
    }

    public void setIdDespesa(Integer idDespesa) {
        this.idDespesa = idDespesa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public DespesaTipo getDespesaTipo() {
        return despesaTipo;
    }

    public void setDespesaTipo(DespesaTipo despesaTipo) {
        this.despesaTipo = despesaTipo;
    }

    public Double getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(Double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

            


    
}
