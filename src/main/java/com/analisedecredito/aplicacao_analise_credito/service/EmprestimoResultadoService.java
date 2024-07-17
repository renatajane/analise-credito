package com.analisedecredito.aplicacao_analise_credito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoReadDto;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoResultadoRepository;

@Service
public class EmprestimoResultadoService {
    
    @Autowired
    EmprestimoResultadoRepository repository;

    public EmprestimoResultadoReadDto findById(Integer id){
        return new EmprestimoResultadoReadDto(repository.findById(id).get());
    }
}
