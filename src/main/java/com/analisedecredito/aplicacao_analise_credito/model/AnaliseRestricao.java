package com.analisedecredito.aplicacao_analise_credito.model;

import com.analisedecredito.aplicacao_analise_credito.dto.AnaliseRestricaoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="analise_restricao")
public class AnaliseRestricao {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_restricao")
    private Integer idRestricao;

    @ManyToOne
    @JoinColumn(name="id_cliente_fk", referencedColumnName = "id_cliente", nullable = false )
    private Cliente cliente;

    @Column(name="status_serasa", nullable = false)
    private Boolean statusSerasa;

    @Column(name="status_spc", nullable = false)
    private Boolean statusSpc;

    // Construtor
    public AnaliseRestricao() {
        
    }

    public AnaliseRestricao(AnaliseRestricaoDto analiseRestricaoDto, Cliente cliente) {
        this.idRestricao = analiseRestricaoDto.getIdRestricao();
        this.statusSerasa = analiseRestricaoDto.getStatusSerasa();
        this.statusSpc = analiseRestricaoDto.getStatusSpc();
        this.cliente = cliente;
        }

    // Getters e Setters
    public Integer getIdRestricao() {
        return idRestricao;
    }

    public void setIdRestricao(Integer idRestricao) {
        this.idRestricao = idRestricao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getStatusSerasa() {
        return statusSerasa;
    }

    public void setStatusSerasa(Boolean statusSerasa) {
        this.statusSerasa = statusSerasa;
    }

    public Boolean getStatusSpc() {
        return statusSpc;
    }

    public void setStatusSpc(Boolean statusSpc) {
        this.statusSpc = statusSpc;
    }
    
            
}
