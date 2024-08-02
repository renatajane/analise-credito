package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.AnaliseRestricaoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.AnaliseRestricaoReadDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.model.AnaliseRestricao;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.repository.AnaliseRestricaoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;

@Service
public class AnaliseRestricaoService {

    @Autowired
    AnaliseRestricaoRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    /* Retorna uma analise de restrição de acordo com o id */
    public AnaliseRestricaoReadDto findById(Integer id) {
        return new AnaliseRestricaoReadDto(repository.findById(id).get());
    }

    /* Retorna uma lista de análises de restrições cadastrados */
    public List<AnaliseRestricaoReadDto> list() {
        List<AnaliseRestricao> listaAnalises = repository.findAll();
        return listaAnalises.stream().map(AnaliseRestricaoReadDto::new).toList();

    }

    /* Cria uma nova análise de restrição com base nos dados fornecidos */
    public void create(AnaliseRestricaoDto analiseRestricaoDto) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(analiseRestricaoDto.getCliente());
        if (clienteOpt.isPresent()) {
            // Cria uma nova instância de AnaliseRestricao
            AnaliseRestricao analiseRestricao = new AnaliseRestricao();

            // Associa o cliente encontrado à análise de restrição
            analiseRestricao.setCliente(clienteOpt.get());

            // Define os status Serasa e SPC
            analiseRestricao.setStatusSerasa(analiseRestricaoDto.getStatusSerasa());
            analiseRestricao.setStatusSpc(analiseRestricaoDto.getStatusSpc());

            // Salva a nova análise de restrição no banco de dados
            repository.save(analiseRestricao);
        } else {
            throw new ResourceNotFoundException(
                    "Cliente não encontrado com id " + analiseRestricaoDto.getCliente());
        }
    }

    /* Atualiza os dados de uma análise de restrição existente */
    public AnaliseRestricaoDto update(Integer id, AnaliseRestricaoDto analiseRestricaoDto) {
        Optional<AnaliseRestricao> analiseOpt = repository.findById(id);
        if (analiseOpt.isPresent()) {
            AnaliseRestricao analiseRestricao = analiseOpt.get();
            Optional<Cliente> clienteOpt = clienteRepository.findById(analiseRestricaoDto.getCliente());
            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                analiseRestricao.setCliente(cliente);
                analiseRestricao.setStatusSerasa(analiseRestricaoDto.getStatusSerasa());
                analiseRestricao.setStatusSpc(analiseRestricaoDto.getStatusSpc());
                AnaliseRestricao updatedAnaliseRestricao = repository.save(analiseRestricao);
                return new AnaliseRestricaoDto(updatedAnaliseRestricao);
            } else {
                throw new ResourceNotFoundException(
                        "Perfil de crédito não encontrado com id " + analiseRestricaoDto.getCliente());
            }
        } else {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }
    }

    /* Remove uma análise de restrição pelo id */
    public void delete(Integer id) {
        AnaliseRestricao analiseRestricao = repository.findById(id).get();
        repository.delete(analiseRestricao);
    }

}
