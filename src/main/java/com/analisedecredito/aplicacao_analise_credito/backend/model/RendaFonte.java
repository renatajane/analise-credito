package com.analisedecredito.aplicacao_analise_credito.backend.model;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.RendaFonteDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="renda_fonte")
public class RendaFonte {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_renda_fonte")
    private Integer idRendaFonte;

    @ManyToOne
    @JoinColumn(name="id_cliente_fk", referencedColumnName = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="id_renda_tipo_fk", referencedColumnName = "id_renda_tipo", nullable = false)
    private RendaTipo rendaTipo;

    @Column(name="valor_renda")
    private Double valorRenda;
    
    // Construtor
    public RendaFonte() {
       
    }
    
    public RendaFonte(RendaFonteDto rendaFonteDto, Cliente cliente, RendaTipo rendaTipo) {
        this.idRendaFonte = rendaFonteDto.getIdRendaFonte();
        this.cliente = cliente;
        this.rendaTipo = rendaTipo;
        this.valorRenda = rendaFonteDto.getValorRenda();
    }

    // Getters e Setters
    public Integer getIdRendaFonte() {
        return idRendaFonte;
    }

    public void setIdRendaFonte(Integer idRendaFonte) {
        this.idRendaFonte = idRendaFonte;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public RendaTipo getRendaTipo() {
        return rendaTipo;
    }

    public void setRendaTipo(RendaTipo rendaTipo) {
        this.rendaTipo = rendaTipo;
    }

    public Double getValorRenda() {
        return valorRenda;
    }

    public void setValorRenda(Double valorRenda) {
        this.valorRenda = valorRenda;
    }

}
