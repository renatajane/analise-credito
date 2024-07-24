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
    private String cpf;

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

    public boolean validaCpf(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false; // CPF deve ter 11 dígitos e não ser uma sequência repetida
        }

        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Character.getNumericValue(cpf.charAt(i));
            }

            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += digits[i] * (10 - i);
            }
            int firstDigit = (sum * 10) % 11;
            if (firstDigit == 10) firstDigit = 0;

            if (firstDigit != digits[9]) return false;

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += digits[i] * (11 - i);
            }
            int secondDigit = (sum * 10) % 11;
            if (secondDigit == 10) secondDigit = 0;

            return secondDigit == digits[10];
        } catch (Exception e) {
            return false;
        }
    }
}

