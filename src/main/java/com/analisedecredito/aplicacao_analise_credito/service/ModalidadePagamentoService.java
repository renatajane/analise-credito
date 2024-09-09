package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.ModalidadePagamentoDto;
import com.analisedecredito.aplicacao_analise_credito.model.ModalidadePagamento;
import com.analisedecredito.aplicacao_analise_credito.repository.ModalidadePagamentoRepository;

@Service
public class ModalidadePagamentoService {

    @Autowired
    ModalidadePagamentoRepository repository;

    /* Retorna uma modalidade de pagamento de acordo com o id */
    public ModalidadePagamentoDto findById(Integer id) {
        return new ModalidadePagamentoDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todas as modalidades de pagamento cadastrados */
    public List<ModalidadePagamentoDto> list() {
        List<ModalidadePagamento> listaModalidades = repository.findAll();
        return listaModalidades.stream().map(ModalidadePagamentoDto::new).toList();
    }

    /* Cria um nova modalidade de pagamento com base nos dados fornecidos */
    public void create(ModalidadePagamentoDto modalidadePagamentoDto) {
        ModalidadePagamento modalidadePagamento = new ModalidadePagamento(modalidadePagamentoDto);
        repository.save(modalidadePagamento);
    }

    /* Atualiza os dados de modalidade de pagamento existente */
    public ModalidadePagamentoDto update(ModalidadePagamentoDto modalidadePagamentoDto) {
        ModalidadePagamento modalidadePagamento = new ModalidadePagamento(modalidadePagamentoDto);
        return new ModalidadePagamentoDto(repository.save(modalidadePagamento));
    }

    /* Remove um dado de modalidade de pagamento pelo id */
    public void delete(Integer id) {
        ModalidadePagamento modalidadePagamento = repository.findById(id).get();
        repository.delete(modalidadePagamento);
    }
}
