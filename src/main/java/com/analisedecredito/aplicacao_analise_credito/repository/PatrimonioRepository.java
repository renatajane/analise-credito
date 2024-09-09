package com.analisedecredito.aplicacao_analise_credito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.Patrimonio;

@Repository
public interface PatrimonioRepository extends JpaRepository<Patrimonio, Integer> {

    @Query("SELECT COALESCE(SUM(p.valorPatrimonio), 0) FROM Patrimonio p WHERE p.cliente.idCliente = :clienteId")
    Double findPatrimonioTotalCliente(@Param("clienteId") Integer clienteId);

    @Query("SELECT p FROM Patrimonio p JOIN p.cliente c WHERE c.idCliente = :id")
    List<Patrimonio> findByIdCliente(@Param("id") Integer id);

    @Query("SELECT p FROM Patrimonio p JOIN p.cliente c WHERE c.cpf = :cpf")
    List<Patrimonio> findByCpfCliente(@Param("cpf") String cpf);

    void deleteByClienteIdCliente(Integer idCliente);
}
