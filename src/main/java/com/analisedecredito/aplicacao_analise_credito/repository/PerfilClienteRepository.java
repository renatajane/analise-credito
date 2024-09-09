package com.analisedecredito.aplicacao_analise_credito.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.PerfilCliente;

@Repository
public interface PerfilClienteRepository extends JpaRepository<PerfilCliente, Integer> {

    // Método para encontrar um perfil de cliente pelo nome do perfil
    Optional<PerfilCliente> findByNomePerfil(String nomePerfil);

    @Query(value = "SELECT * FROM perfil_cliente p WHERE p.score >= :score ORDER BY p.score LIMIT 1", 
    nativeQuery = true)
    PerfilCliente findScore(@Param("score") Integer score);
    

}
