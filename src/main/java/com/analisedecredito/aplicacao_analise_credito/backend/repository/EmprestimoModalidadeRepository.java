package com.analisedecredito.aplicacao_analise_credito.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoModalidade;

@Repository
public interface EmprestimoModalidadeRepository extends JpaRepository<EmprestimoModalidade, Integer> {

}
