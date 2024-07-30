package com.analisedecredito.aplicacao_analise_credito.model;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "emprestimo_parcela")
public class EmprestimoParcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emprestimo_parcela")
    private Integer idEmprestimoParcela;

    @Column(name = "valor_parcela", nullable = false)
    private Double valorParcela;

    @Column(name = "data_vencimento", nullable = false)
    private Date dataVencimento;

    // Getters and Setters
    public Integer getIdEmprestimoParcela() {
        return idEmprestimoParcela;
    }

    public void setIdEmprestimoParcela(Integer idEmprestimoParcela) {
        this.idEmprestimoParcela = idEmprestimoParcela;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
