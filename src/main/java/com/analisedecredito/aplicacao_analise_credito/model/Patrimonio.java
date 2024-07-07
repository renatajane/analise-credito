package com.analisedecredito.aplicacao_analise_credito.model;

import com.analisedecredito.aplicacao_analise_credito.dto.PatrimonioDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="patrimonio")
public class Patrimonio {
    
    //Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_patrimonio")
    private Integer idPatrimonio;

    @ManyToOne
    @JoinColumn(name="id_cliente_fk", referencedColumnName = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="id_patrimonio_tipo_fk", referencedColumnName = "id_patrimonio_tipo", nullable = false)
    private PatrimonioTipo patrimonioTipo;

    @Column(name="valor_patrimonio")
    private Double valorPatrimonio;

    // Construtor
    public Patrimonio() {
        
    }

    public Patrimonio(PatrimonioDto patrimonioDto, Cliente cliente, PatrimonioTipo patrimonioTipo) {
        this.idPatrimonio = patrimonioDto.getIdPatrimonio();
        this.cliente = cliente;
        this.patrimonioTipo = patrimonioTipo;
        this.valorPatrimonio = patrimonioDto.getValorPatrimonio();
    }

    // Getters e Setters
    public Integer getIdPatrimonio() {
        return idPatrimonio;
    }

    public void setIdPatrimonio(Integer idPatrimonio) {
        this.idPatrimonio = idPatrimonio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PatrimonioTipo getPatrimonioTipo() {
        return patrimonioTipo;
    }

    public void setPatrimonioTipo(PatrimonioTipo patrimonioTipo) {
        this.patrimonioTipo = patrimonioTipo;
    }

    public Double getValorPatrimonio() {
        return valorPatrimonio;
    }

    public void setValorPatrimonio(Double valorPatrimonio) {
        this.valorPatrimonio = valorPatrimonio;
    }

}
