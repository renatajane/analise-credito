package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Despesa;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Patrimonio;
import com.analisedecredito.aplicacao_analise_credito.backend.model.RendaFonte;

public class ClienteCompletoReadDto {

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
    private boolean spcSerasa;
    private Double valorMaximoPreAprovado;

    // Financeiros
    private List<RendaFonte> rendas = new ArrayList<>();
    private List<Patrimonio> patrimonios = new ArrayList<>();
    private List<Despesa> despesas = new ArrayList<>();

    // Construtor
    public ClienteCompletoReadDto() {
    }

    public ClienteCompletoReadDto(Cliente cliente, List<RendaFonte> renda, List<Patrimonio> patrimonio,
            List<Despesa> despesa) {
        BeanUtils.copyProperties(cliente, this);
        this.rendas = renda;
        this.despesas = despesa;
        this.patrimonios = patrimonio;
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

    public List<RendaFonte> getRendas() {
        return rendas;
    }

    public void setRenda(List<RendaFonte> rendas) {
        this.rendas = rendas;
    }

    public List<Patrimonio> getPatrimonios() {
        return patrimonios;
    }

    public void setPatrimonios(List<Patrimonio> patrimonios) {
        this.patrimonios = patrimonios;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public boolean getSpcSerasa() {
        return spcSerasa;
    }

    public void setSpcSerasa(boolean spcSerasa) {
        this.spcSerasa = spcSerasa;
    }

    public Double getValorMaximoPreAprovado() {
        return valorMaximoPreAprovado;
    }

    public void setValorMaximoPreAprovado(Double valorMaximoPreAprovado) {
        this.valorMaximoPreAprovado = valorMaximoPreAprovado;
    }

    public void setRendas(List<RendaFonte> rendas) {
        this.rendas = rendas;
    }

}
