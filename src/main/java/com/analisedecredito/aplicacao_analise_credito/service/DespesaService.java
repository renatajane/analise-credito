package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.DespesaDto;
import com.analisedecredito.aplicacao_analise_credito.dto.DespesaReadDto;
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
    public DespesaReadDto findById(Integer id) {
        return new DespesaReadDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todas as despesas cadastrados */
    public List<DespesaReadDto> list() {
        List<Despesa> listaDespesa = repository.findAll();
        return listaDespesa.stream().map(DespesaReadDto::new).toList();
    }

    /* Cria uma nova despesa com base nos dados fornecidos */
    public DespesaDto create(DespesaDto despesaDto) {

        Optional<Cliente> clienteOpt = clienteRepository.findById(despesaDto.getCliente());
        Optional<DespesaTipo> despesaTipoOpt = despesaTipoRepository.findById(despesaDto.getDespesaTipo());

        if (clienteOpt.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado com o id " + despesaDto.getCliente());
        }
        if (despesaTipoOpt.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Tipo de despesa não encontrado com o id " + despesaDto.getDespesaTipo());
        }

        Despesa despesa = new Despesa();
        despesa.setIdDespesa(despesaDto.getIdDespesa());
        despesa.setCliente(clienteOpt.get());
        despesa.setDespesaTipo(despesaTipoOpt.get());
        despesa.setValorDespesa(despesaDto.getValorDespesa());
        repository.save(despesa);

        return new DespesaDto(despesa);
    }

    public DespesaDto update(Integer id,DespesaDto despesaDto) {
          
        Optional<Despesa> despesaOpt = repository.findById(id);
        if (despesaOpt.isPresent()) {
            Despesa despesa = despesaOpt.get();
            Optional<Cliente> clienteOpt = clienteRepository.findById(despesaDto.getCliente());
            Optional<DespesaTipo> despesaTipoOpt = despesaTipoRepository.findById(despesaDto.getDespesaTipo());

            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                DespesaTipo despesaTipo = despesaTipoOpt.get();
                despesa.setCliente(cliente);
                despesa.setDespesaTipo(despesaTipo);
                despesa.setIdDespesa(despesaDto.getIdDespesa());
                despesa.setValorDespesa(despesaDto.getValorDespesa());

                Despesa updatedDespesa= repository.save(despesa);
                return new DespesaDto(updatedDespesa);
            } else {
                throw new ResourceNotFoundException(
                        "Perfil de crédito não encontrado com id " + despesaDto.getCliente());
            }
        } else {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }
    }

}
