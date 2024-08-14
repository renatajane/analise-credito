package com.analisedecredito.aplicacao_analise_credito.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.backend.model.Patrimonio;

@Repository
public interface PatrimonioRepository extends JpaRepository<Patrimonio, Integer> {
    
    @Query("SELECT COALESCE(SUM(p.valorPatrimonio), 0) FROM Patrimonio p WHERE p.cliente.idCliente = :clienteId")
    Double findPatrimonioTotalCliente(@Param("clienteId") Integer clienteId);
}
