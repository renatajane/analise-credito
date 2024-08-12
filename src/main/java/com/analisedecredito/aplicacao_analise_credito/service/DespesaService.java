package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.DespesaDto;
import com.analisedecredito.aplicacao_analise_credito.dto.DespesaReadDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.Despesa;
import com.analisedecredito.aplicacao_analise_credito.model.DespesaTipo;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.DespesaRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.DespesaTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoRequisicaoRepository;

@Service
public class DespesaService {

    @Autowired
    DespesaRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    DespesaTipoRepository despesaTipoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    EmprestimoRequisicaoRepository emprestimoRequisicaoRepository;

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

        // Verifica se o cliente e o tipo de despesa existem
        Optional<Cliente> clienteOpt = clienteRepository.findById(despesaDto.getCliente());
        Optional<DespesaTipo> despesaTipoOpt = despesaTipoRepository.findById(despesaDto.getDespesaTipo());

        if (clienteOpt.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado com o id " + despesaDto.getCliente());
        }
        if (despesaTipoOpt.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Tipo de despesa não encontrado com o id " + despesaDto.getDespesaTipo());
        }

        Despesa despesa = new Despesa(despesaDto, clienteOpt.get(), despesaTipoOpt.get());
        despesa.setValorDespesa(despesaDto.getValorDespesa());
        despesa.setDespesaTipo(despesaTipoOpt.get());
        repository.save(despesa);

        // Define o perfil do cliente após criar a despesa
        clienteService.definePerfilCliente(clienteOpt.get().getIdCliente());

        // Calcula a despesa total incluindo todas as despesas e parcelas de empréstimos
        Double despesaTotal = calculaDespesaTotal(clienteOpt.get().getIdCliente());
        despesaDto.setDespesaTotal(despesaTotal); 

        // Atualiza o ID gerado no DTO
        despesaDto.setIdDespesa(despesa.getIdDespesa());

        return despesaDto;
    }

    public Double calculaDespesaTotal(Integer idCliente) {

        // Verifica se o cliente existe
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado com o id " + idCliente);
        }

        Cliente cliente = clienteOpt.get();

        // Calcula a soma de todas as despesas do cliente
        Double despesaTotal = repository.findDespesaTotalCliente(cliente.getIdCliente());

        // Verifica se o cliente tem requisições de empréstimo ativas
        List<EmprestimoRequisicao> emprestimosAtivos = emprestimoRequisicaoRepository
                .findRequisicaoByIdCliente(cliente.getIdCliente());

        // Soma os valores das parcelas das requisições ativas às despesas totais
        if (emprestimosAtivos != null && !emprestimosAtivos.isEmpty()) {
            for (EmprestimoRequisicao emprestimo : emprestimosAtivos) {
                despesaTotal += emprestimo.getValorParcela();
            }
        }

        return despesaTotal;
    }

    /* Atualiza os dados de uma despesa existente */
    public DespesaDto update(Integer id, DespesaDto despesaDto) {

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

                Despesa updatedDespesa = repository.save(despesa);

                clienteService.definePerfilCliente(clienteOpt.get().getIdCliente());

                return new DespesaDto(updatedDespesa);
            } else {
                throw new ResourceNotFoundException(
                        "Perfil de crédito não encontrado com id " + despesaDto.getCliente());
            }
        } else {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }
    }

    /* Remove uma despesa pelo id */
    public void delete(Integer id) {
        Despesa despesa = repository.findById(id).get();
        repository.delete(despesa);

        clienteService.definePerfilCliente(despesa.getCliente().getIdCliente());
    }

}
