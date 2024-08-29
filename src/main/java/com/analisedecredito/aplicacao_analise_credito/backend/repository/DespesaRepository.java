package com.analisedecredito.aplicacao_analise_credito.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.backend.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

    @Query("SELECT COALESCE(SUM(p.valorDespesa), 0) FROM Despesa p WHERE p.cliente.idCliente = :clienteId")
    Double findDespesaTotalCliente(@Param("clienteId") Integer clienteId);

    @Query("SELECT d FROM Despesa d JOIN d.cliente c WHERE c.idCliente = :id")
    List<Despesa> findByIdCliente(@Param("id") Integer id);
}
