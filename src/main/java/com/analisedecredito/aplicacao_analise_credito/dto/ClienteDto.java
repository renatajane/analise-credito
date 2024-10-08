package com.analisedecredito.aplicacao_analise_credito.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.model.Cliente;

public class ClienteDto {

    // Propriedades
    private Integer idCliente;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private String telefone;
    private String endereco;
    private boolean autorizacaoLGPD;
    private Date dataAutorizacaoLGPD;
    private Integer perfilCliente;
    private Double rendaTotal;
    private Double patrimonioTotal;
    private Double despesaTotal;
    private Boolean spcSerasa;
    private Double valorMaximoPreAprovado;

    // Construtor
    public ClienteDto() {
    }

    public ClienteDto(Cliente cliente) {
        BeanUtils.copyProperties(cliente, this);
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
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

    public Date getDataAutorizacaoLGPD() {
        return dataAutorizacaoLGPD;
    }

    public void setDataAutorizacaoLGPD(Date dataAutorizacaoLGPD) {
        this.dataAutorizacaoLGPD = dataAutorizacaoLGPD;
    }

    public Double getRendaTotal() {
        return rendaTotal;
    }

    public void setRendaTotal(Double rendaTotal) {
        this.rendaTotal = rendaTotal;
    }

    public Integer getPerfilCliente() {
        return perfilCliente;
    }

    public void setPerfilCliente(Integer perfilCliente) {
        this.perfilCliente = perfilCliente;
    }

    public Double getPatrimonioTotal() {
        return patrimonioTotal;
    }

    public void setPatrimonioTotal(Double patrimonioTotal) {
        this.patrimonioTotal = patrimonioTotal;
    }

    public Double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(Double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    public Boolean getSpcSerasa() {
        return spcSerasa;
    }

    public void setSpcSerasa(Boolean spcSerasa) {
        this.spcSerasa = spcSerasa;
    }

    public Double getValorMaximoPreAprovado() {
        return valorMaximoPreAprovado;
    }

    public void setValorMaximoPreAprovado(Double valorMaximoPreAprovado) {
        this.valorMaximoPreAprovado = valorMaximoPreAprovado;
    }
}
