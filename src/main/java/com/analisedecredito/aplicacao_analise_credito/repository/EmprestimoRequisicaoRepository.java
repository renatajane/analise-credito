package com.analisedecredito.aplicacao_analise_credito.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;

@Repository
public interface EmprestimoRequisicaoRepository extends JpaRepository<EmprestimoRequisicao, Integer> {

    @Query("SELECT a FROM EmprestimoRequisicao a WHERE a.cliente.idCliente = :clienteId")
    List<EmprestimoRequisicao> findRequisicaoByIdCliente(@Param("clienteId") Integer clienteId);

    @Query("SELECT er FROM EmprestimoRequisicao er JOIN er.cliente c WHERE c.cpf = :cpf")
    List<EmprestimoRequisicao> findByClienteCpf(@Param("cpf") String cpf);

    @Query("SELECT er FROM EmprestimoRequisicao er WHERE er.dataRequisicao BETWEEN :inicio AND :fim")
    List<EmprestimoRequisicao> findByDataCriacao(@Param("inicio") Date inicio, @Param("fim") Date fim);

    @Query("SELECT er FROM EmprestimoRequisicao er WHERE er.cliente.id = :clienteId AND er.aprovado = :aprovado")
    List<EmprestimoRequisicao> findRequisicaoByIdClienteAndStatus(
            @Param("clienteId") Integer clienteId,
            @Param("aprovado") Boolean aprovado);

    @Query("SELECT a FROM EmprestimoRequisicao a WHERE a.cliente.idCliente = :clienteId AND a.aprovado = true")
    List<EmprestimoRequisicao> findRequisicaoByIdClienteAndAprovado(@Param("clienteId") Integer clienteId);

    // Adicionada consulta para CPF e período
    @Query("SELECT er FROM EmprestimoRequisicao er JOIN er.cliente c WHERE c.cpf = :cpf AND er.dataRequisicao >= :inicio AND er.dataRequisicao <= :fim")
    List<EmprestimoRequisicao> findByCpfAndDataRequisicao(
            @Param("cpf") String cpf,
            @Param("inicio") Date inicio,
            @Param("fim") Date fim);
}
