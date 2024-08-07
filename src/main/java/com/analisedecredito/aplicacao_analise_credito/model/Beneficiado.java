package com.analisedecredito.aplicacao_analise_credito.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Beneficiado {

    @NotNull(message = "O nome do beneficiado não pode ser nulo")
    @Size(min = 1, max = 100, message = "O nome do beneficiado deve ter entre 1 e 100 caracteres")
    private String nome;

    @NotNull(message = "O CPF do beneficiado não pode ser nulo")
    @Size(min = 11, max = 14, message = "O CPF do beneficiado deve ter entre 11 e 14 caracteres")
    private String cpf;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    
}
