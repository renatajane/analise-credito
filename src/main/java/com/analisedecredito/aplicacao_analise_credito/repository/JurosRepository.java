package com.analisedecredito.aplicacao_analise_credito.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.Juros;

@Repository
public interface JurosRepository extends JpaRepository<Juros, Integer> {

    @Query("SELECT j FROM Juros j WHERE j.dataVigencia >= :data")
    Juros findByDataJuros(@Param("data") Date data);
}
