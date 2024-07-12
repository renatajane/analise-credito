package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.DespesaDto;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.Despesa;
import com.analisedecredito.aplicacao_analise_credito.model.DespesaTipo;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.DespesaRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.DespesaTipoRepository;

@Service
public class DespesaService {

    @Autowired
    DespesaRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    DespesaTipoRepository despesaTipoRepository;

    /* Retorna despesa de acordo com o id */
    public DespesaDto findById(Integer id) {
        Optional<Despesa> despesaOpt = repository.findById(id);

        if (despesaOpt.isPresent()) {
            Despesa despesa = despesaOpt.get();
            return new DespesaDto(despesa, despesa.getCliente(), despesa.getDespesaTipo());
        } else {
            throw new RuntimeException("Despesa não encontrada com o ID: " + id);
        }
    }

    /* Retorna uma lista de todas as despesas cadastrados */
    public List<DespesaDto> list() {
        List<Despesa> listaDespesa = repository.findAll();
        return listaDespesa.stream()
                .map(despesa -> new DespesaDto(despesa, despesa.getCliente(), despesa.getDespesaTipo()))
                .collect(Collectors.toList());
    }

    /* Cria uma nova despesa com base nos dados fornecidos */
    public DespesaDto create(DespesaDto despesaDto) {
        Cliente cliente = clienteRepository.findById(despesaDto.getCliente().getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + despesaDto.getDespesaTipo().getIdDespesaTipo()));

        DespesaTipo despesaTipo = despesaTipoRepository.findById(despesaDto.getDespesaTipo().getIdDespesaTipo())
                .orElseThrow(() -> new RuntimeException("Tipo de Despesa não encontrado com o ID: " + despesaDto.getDespesaTipo().getIdDespesaTipo()));

        Despesa despesa = new Despesa();
        despesa.setCliente(cliente);
        despesa.setDespesaTipo(despesaTipo);
        despesa.setValorDespesa(despesaDto.getValorDespesa());
        repository.save(despesa);

        return new DespesaDto(despesa, cliente, despesaTipo);
    }
}
