package com.analisedecredito.aplicacao_analise_credito.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.AnaliseRestricao;

@Repository
public interface AnaliseRestricaoRepository extends JpaRepository<AnaliseRestricao, Integer> {

    @Query("SELECT a FROM AnaliseRestricao a WHERE a.cliente.idCliente = :clienteId")
    Optional<AnaliseRestricao> findByClienteId(@Param("clienteId") Integer clienteId);
}
