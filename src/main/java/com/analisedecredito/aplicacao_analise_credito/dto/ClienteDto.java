package com.analisedecredito.aplicacao_analise_credito.dto;

import java.util.Date;

public class ClienteDto {

    // Propriedades
    private Integer idCliente;
    private String nome;
    private Integer cpf;
    private Date dataNascimento;
    private String email;
    private String telefone;
    private String endereco;
    private boolean autorizacaoLGPD;
    private Integer idPerfilCredito;

    // Construtor
    public ClienteDto() {
    }

    // Getters e Setters
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCpf() {
        return cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean getAutorizacaoLGPD() {
        return autorizacaoLGPD;
    }

    public void setAutorizacaoLGPD(boolean autorizacaoLGPD) {
        this.autorizacaoLGPD = autorizacaoLGPD;
    }

    public Integer getIdPerfilCredito() {
        return idPerfilCredito;
    }

    public void setIdPerfilCredito(Integer idPerfilCredito) {
        this.idPerfilCredito = idPerfilCredito;
    }

    
}
