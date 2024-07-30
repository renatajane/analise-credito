package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.ClienteDto;
import com.analisedecredito.aplicacao_analise_credito.dto.ClienteReadDto;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.PerfilCliente;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoRequisicaoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.PerfilClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaFonteRepository;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    private PerfilClienteRepository perfilClienteRepository;

    @Autowired
    EmprestimoRequisicaoRepository requisicaoRepository;

    @Autowired
    RendaFonteRepository rendaRepository;

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

        if (!clienteDto.getAutorizacaoLGPD()) {
            throw new IllegalArgumentException(
                    "O cliente deve autorizar o tratamento de seus dados de acordo com a LGPD.");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDto.getNome());
        cliente.setCpf(clienteDto.getCpf());
        cliente.setAutorizacaoLGPD(clienteDto.getAutorizacaoLGPD());
        cliente.setDataAutorizacaoLGPD(new Date());
        cliente.setDataNascimento(clienteDto.getDataNascimento());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setEndereco(clienteDto.getEndereco());
        cliente.setIdCliente(clienteDto.getIdCliente());
        cliente.setTelefone(clienteDto.getTelefone());
        cliente.setPerfilCliente(null);
        repository.save(cliente);
        // } else {
        // throw new ResourceNotFoundException("Perfil de crédito não econtrado.");
        // }
    }

    /* Atualiza os dados de um cliente existente */
    public ClienteDto update(Integer id, ClienteDto clienteDto) {
        Optional<Cliente> clienteOpt = repository.findById(id);

        if (!clienteDto.getAutorizacaoLGPD()) {
            throw new IllegalArgumentException(
                    "O cliente deve autorizar o tratamento de seus dados de acordo com a LGPD.");
        }

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            Optional<PerfilCliente> perfilClienteOpt = perfilClienteRepository.findById(clienteDto.getPerfilCliente());
            if (perfilClienteOpt.isPresent()) {
                PerfilCliente perfilCliente = perfilClienteOpt.get();
                cliente.setNome(clienteDto.getNome());
                cliente.setCpf(clienteDto.getCpf());
                cliente.setDataNascimento(clienteDto.getDataNascimento());
                cliente.setEmail(clienteDto.getEmail());
                cliente.setTelefone(clienteDto.getTelefone());
                cliente.setEndereco(clienteDto.getEndereco());
                cliente.setAutorizacaoLGPD(clienteDto.getAutorizacaoLGPD());
                cliente.setPerfilCliente(perfilCliente);
                Cliente updatedCliente = repository.save(cliente);
                return new ClienteDto(updatedCliente);
            } else {
                throw new ResourceNotFoundException(
                        "Perfil de crédito não encontrado com id " + clienteDto.getPerfilCliente());
            }
        } else {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }
    }

    // Calcula renda total do cliente
    public void somaRenda(ClienteDto clienteDto){
        Double valorTotal = rendaRepository.findRendaTotalCliente(clienteDto.getIdCliente());
        clienteDto.setRendaTotal(valorTotal);
    }

    /* Remove um cliente pelo id */
    public void delete(Integer id) {
        Cliente cliente = repository.findById(id).get();
        repository.delete(cliente);
    }

}
