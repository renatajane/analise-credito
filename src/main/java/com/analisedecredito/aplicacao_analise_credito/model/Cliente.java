package com.analisedecredito.aplicacao_analise_credito.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente {

    // Propriedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Size(min = 1, max = 100, message = "Nome deve ter entre 1 e 100 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Valid
    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private Date dataNascimento;

    @Email(message = "Email deve ser válido")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Telefone deve seguir o formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX")
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Size(min = 1, max = 255, message = "Endereço deve ter entre 1 e 255 caracteres")
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "autorizacao_lgpd", nullable = false)
    private boolean autorizacaoLGPD;

    @ManyToOne
    @JoinColumn(name = "id_perfil_credito_fk", referencedColumnName = "id_perfil_credito")
    private PerfilCredito perfilCredito;

    // Construtor
    public Cliente() {

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
        if (dataNascimento == null) {
            throw new IllegalArgumentException("Data de nascimento não pode ser nula");
        }
        if (dataNascimento.after(new Date())) {
            throw new IllegalArgumentException("Data de nascimento deve ser no passado");
        }
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
