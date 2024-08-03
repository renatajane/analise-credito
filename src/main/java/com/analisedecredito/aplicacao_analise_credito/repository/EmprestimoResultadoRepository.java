// package com.analisedecredito.aplicacao_analise_credito.repository;

// import java.util.Date;
// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoResultado;

// @Repository
// public interface EmprestimoResultadoRepository extends JpaRepository<EmprestimoResultado, Integer> {

//     @Query("SELECT er FROM EmprestimoResultado er " +
//             "JOIN er.emprestimoRequisicao req " +
//             "JOIN req.cliente c " +
//             "WHERE c.cpf = :cpf")
//     List<EmprestimoResultado> findByClienteCpf(@Param("cpf") String cpf);

//     @Query("SELECT er FROM EmprestimoResultado er " +
//             "JOIN er.emprestimoRequisicao req " +
//             "WHERE req.dataRequisicao BETWEEN :inicio AND :fim")
//     List<EmprestimoResultado> findByDataCriacao(@Param("inicio") Date inicio, @Param("fim") Date fim);
// }
