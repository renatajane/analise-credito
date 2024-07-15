package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.ClienteDto;
import com.analisedecredito.aplicacao_analise_credito.dto.ClienteReadDto;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.PerfilCredito;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.PerfilCreditoRepository;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    private PerfilCreditoRepository perfilCreditoRepository;

    /* Retorna um cliente de acordo com o id */
    public ClienteReadDto findById(Integer id) {
        return new ClienteReadDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todos os clientes cadastrados */
    public List<ClienteReadDto> list() {
        List<Cliente> listaClientes = repository.findAll();
        return listaClientes.stream().map(ClienteReadDto::new).toList();
    }

    /* Cria um novo cliente com base nos dados fornecidos */
    public void create(ClienteDto clienteDto) {
       // Optional<PerfilCredito> perfilCreditoOpt = perfilCreditoRepository.findById(clienteDto.getPerfilCredito());
        //if (perfilCreditoOpt.isPresent()) {
            Cliente cliente = new Cliente();
            cliente.setNome(clienteDto.getNome());
            cliente.setCpf(clienteDto.getCpf());
            cliente.setAutorizacaoLGPD(clienteDto.getAutorizacaoLGPD());
            cliente.setDataNascimento(clienteDto.getDataNascimento());
            cliente.setEmail(clienteDto.getEmail());
            cliente.setEndereco(clienteDto.getEndereco());
            cliente.setIdCliente(clienteDto.getIdCliente());
            cliente.setTelefone(clienteDto.getTelefone());
            cliente.setPerfilCredito(null);
            repository.save(cliente);
       // } else {
         //   throw new ResourceNotFoundException("Perfil de crédito não econtrado.");
        //}
    }

    /* Atualiza os dados de um cliente existente */
    public ClienteDto update(Integer id, ClienteDto clienteDto) {
        Optional<Cliente> clienteOpt = repository.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            Optional<PerfilCredito> perfilCreditoOpt = perfilCreditoRepository.findById(clienteDto.getPerfilCredito());
            if (perfilCreditoOpt.isPresent()) {
                PerfilCredito perfilCredito = perfilCreditoOpt.get();
                cliente.setNome(clienteDto.getNome());
                cliente.setCpf(clienteDto.getCpf());
                cliente.setDataNascimento(clienteDto.getDataNascimento());
                cliente.setEmail(clienteDto.getEmail());
                cliente.setTelefone(clienteDto.getTelefone());
                cliente.setEndereco(clienteDto.getEndereco());
                cliente.setAutorizacaoLGPD(clienteDto.getAutorizacaoLGPD());
                cliente.setPerfilCredito(perfilCredito);
                Cliente updatedCliente = repository.save(cliente);
                return new ClienteDto(updatedCliente);
            } else {
                throw new ResourceNotFoundException(
                        "Perfil de crédito não encontrado com id " + clienteDto.getPerfilCredito());
            }
        } else {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }
    }

    /* Remove um cliente pelo id */
    public void delete(Integer id) {
        Cliente cliente = repository.findById(id).get();
        repository.delete(cliente);
    }

}
