package com.analisedecredito.aplicacao_analise_credito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.RendaFonte;

@Repository
public interface RendaFonteRepository extends JpaRepository<RendaFonte, Integer> {

    @Query("SELECT COALESCE(SUM(r.valorRenda), 0) FROM RendaFonte r WHERE r.cliente.idCliente = :clienteId")
    Double findRendaTotalCliente(@Param("clienteId") Integer clienteId);
}
