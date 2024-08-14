package com.analisedecredito.aplicacao_analise_credito.backend.dto;

public class BeneficiadoDto {

    // Propriedades
    private String cpf;
    private Double valorBeneficio;

    // Construtor
    public BeneficiadoDto() {
    }

    public BeneficiadoDto(String cpf, Double valorBeneficio) {
        this.cpf = cpf;
        this.valorBeneficio = valorBeneficio;
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getValorBeneficio() {
        return valorBeneficio;
    }

    public void setValorBeneficio(Double valorBeneficio) {
        this.valorBeneficio = valorBeneficio;
    }

}
