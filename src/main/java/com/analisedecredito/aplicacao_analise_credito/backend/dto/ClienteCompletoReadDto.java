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

    // Financeiros
    private List<RendaFonte> renda = new ArrayList<>();
    private List<Patrimonio> patrimonio = new ArrayList<>();
    private List<Despesa> despesa = new ArrayList<>();

    // Construtor
    public ClienteCompletoReadDto() {
    }
    
    public ClienteCompletoReadDto(Cliente cliente, List<RendaFonte> renda, List<Patrimonio> patrimonio, List<Despesa> despesa) {
        BeanUtils.copyProperties(cliente, this);
        this.renda = renda;
        this.despesa = despesa;
        this.patrimonio = patrimonio;        
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
    public boolean isAutorizacaoLGPD() {
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

    public List<RendaFonte> getRenda() {
        return renda;
    }

    public void setRenda(List<RendaFonte> renda) {
        this.renda = renda;
    }

    public List<Patrimonio> getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(List<Patrimonio> patrimonio) {
        this.patrimonio = patrimonio;
    }

    public List<Despesa> getDespesa() {
        return despesa;
    }

    public void setDespesa(List<Despesa> despesa) {
        this.despesa = despesa;
    }


}
