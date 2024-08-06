package com.analisedecredito.aplicacao_analise_credito.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analisedecredito.aplicacao_analise_credito.model.IofAtual;

@Repository
public interface IofAtualRepository extends JpaRepository<IofAtual, Integer>{    
    
    @Query("SELECT i FROM IofAtual i WHERE i.dataCalculo >= :data")
    IofAtual findByDataIof(@Param("data") Date data);  
}
