package com.analisedecredito.aplicacao_analise_credito.model;

import java.util.Date;

import com.analisedecredito.aplicacao_analise_credito.dto.ClienteDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", unique = true, nullable = false)
    private Integer cpf;

    @Column(name = "data_nascimento", nullable = false)
    private Date dataNascimento;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "autorizacao_lgpd", nullable = false)
    private boolean autorizacaoLGPD;

    @ManyToOne
    @JoinColumn(name = "id_perfil_credito_fk", referencedColumnName = "id_perfil_credito", nullable = false)
    private PerfilCredito perfilCredito;

    // Construtor 
    public Cliente() {
        
    }

    public Cliente(ClienteDto clienteDto, PerfilCredito perfilCredito) {
        this.idCliente = clienteDto.getIdCliente();
        this.nome = clienteDto.getNome();
        this.cpf = clienteDto.getCpf();
        this.dataNascimento = clienteDto.getDataNascimento();
        this.email = clienteDto.getEmail();
        this.telefone = clienteDto.getTelefone();
        this.endereco = clienteDto.getEndereco();
        this.autorizacaoLGPD = clienteDto.getAutorizacaoLGPD();
        this.perfilCredito = perfilCredito;
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

    public PerfilCredito getPerfilCredito() {
        return perfilCredito;
    }

    public void setPerfilCredito(PerfilCredito perfilCredito) {
        this.perfilCredito = perfilCredito;
    }
}

