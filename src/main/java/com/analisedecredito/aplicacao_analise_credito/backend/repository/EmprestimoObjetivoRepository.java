package com.analisedecredito.aplicacao_analise_credito.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoObjetivo;

@Repository
public interface EmprestimoObjetivoRepository extends JpaRepository<EmprestimoObjetivo, Integer>{

}
