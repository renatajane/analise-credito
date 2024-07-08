package com.analisedecredito.aplicacao_analise_credito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;

@Repository
public interface EmprestimoRequisicaoRepository extends JpaRepository<EmprestimoRequisicao, Integer> {

}
