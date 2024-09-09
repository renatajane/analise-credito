package com.analisedecredito.aplicacao_analise_credito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.RendaTipo;

@Repository
public interface RendaTipoRepository extends JpaRepository<RendaTipo, Integer>{
    
    // @Query("""
    //         SELECT rt FROM RendaTipo rt WHERE idTipoRenda = :id
    //         """)

    // public String findAllRendaTipos();
}