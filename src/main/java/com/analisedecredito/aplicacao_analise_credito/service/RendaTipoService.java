package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.model.RendaTipo;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaTipoRepository;

@Service
public class RendaTipoService {
    
    @Autowired
    RendaTipoRepository repository;

    public List<RendaTipo> list(){
        return repository.findAll();
    }
}
