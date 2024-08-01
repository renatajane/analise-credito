package com.analisedecredito.aplicacao_analise_credito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;

@Repository
public interface EmprestimoRequisicaoRepository extends JpaRepository<EmprestimoRequisicao, Integer> {

    @Query("SELECT a FROM EmprestimoRequisicao a WHERE a.cliente.idCliente = :clienteId")
    List<EmprestimoRequisicao> findRequisicao(@Param("clienteId") Integer clienteId);

    //List<EmprestimoRequisicao> findById(Cliente cliente);

}
