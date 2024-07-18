package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoReadDto;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoResultado;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoRequisicaoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoResultadoRepository;

@Service
public class EmprestimoResultadoService {

    @Autowired
    EmprestimoResultadoRepository repository;

    @Autowired
    EmprestimoRequisicaoRepository requisicaoRepository;

    /* Retorna um empréstimo resultado de acordo com o id */
    public EmprestimoResultadoReadDto findById(Integer id) {
        return new EmprestimoResultadoReadDto(repository.findById(id).get());
    }

    /* Retorna uma lista de resultados de empréstimo cadastrados */
    public List<EmprestimoResultadoReadDto> list() {
        List<EmprestimoResultado> listaResultado = repository.findAll();
        return listaResultado.stream().map(EmprestimoResultadoReadDto::new).toList();
    }

    /* Cria um novo resultados de empréstim com base nos dados fornecidos */
    public void create(EmprestimoResultadoDto emprestimoResultadoDto) {

        EmprestimoResultado emprestimoResultado = new EmprestimoResultado();
        Optional<EmprestimoRequisicao> requisicaoOpt = requisicaoRepository
                .findById(emprestimoResultadoDto.getEmprestimoRequisicao());

        emprestimoResultado.setIdResultado(emprestimoResultadoDto.getIdResultado());
        emprestimoResultado.setDescricaoResultado(emprestimoResultadoDto.getDescricaoResultado());
        emprestimoResultado.setAprovado(emprestimoResultadoDto.getAprovado());
        emprestimoResultado.setEmprestimoRequisicao(requisicaoOpt.get());

        repository.save(emprestimoResultado);
    }
}
