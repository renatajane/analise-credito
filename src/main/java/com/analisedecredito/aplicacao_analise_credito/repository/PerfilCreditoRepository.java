package com.analisedecredito.aplicacao_analise_credito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.PerfilCredito;

@Repository
public interface PerfilCreditoRepository extends JpaRepository<PerfilCredito, Integer>{
    
}
