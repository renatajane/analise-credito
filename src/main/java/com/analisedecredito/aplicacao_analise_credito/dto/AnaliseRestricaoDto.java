package com.analisedecredito.aplicacao_analise_credito.dto;

import com.analisedecredito.aplicacao_analise_credito.model.AnaliseRestricao;

public class AnaliseRestricaoDto {

    // Propriedades
    private Integer idRestricao;
    private Integer cliente;
    private Boolean statusSerasa;
    private Boolean statusSpc;

    // Construtor
    public AnaliseRestricaoDto() {

    }

    public AnaliseRestricaoDto(AnaliseRestricao analiseRestricao) {
        this.idRestricao = analiseRestricao.getIdRestricao();
        this.cliente = analiseRestricao.getCliente().getIdCliente();
        this.statusSerasa = analiseRestricao.getStatusSerasa();
        this.statusSpc = analiseRestricao.getStatusSpc();
    }

    // Getters e Setters
    public Integer getIdRestricao() {
        return idRestricao;
    }

    public void setIdRestricao(Integer idRestricao) {
        this.idRestricao = idRestricao;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
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
