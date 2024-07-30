package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.ClienteReadDto;
import com.analisedecredito.aplicacao_analise_credito.dto.PerfilClienteDto;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.PerfilCliente;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.PerfilClienteRepository;

@Service
public class PerfilClienteService {

    @Autowired
    private PerfilClienteRepository perfilClienteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /* Retorna um perfil de crédito de acordo com o id */
    public PerfilClienteDto findById(Integer id) {
        Optional<PerfilCliente> perfilClienteOpt = perfilClienteRepository.findById(id);
        return perfilClienteOpt.map(PerfilClienteDto::new).orElse(null);
    }

    /* Retorna uma lista de perfis de créditos cadastrados */
    public List<PerfilClienteDto> list() {
        List<PerfilCliente> listPerfilCliente = perfilClienteRepository.findAll();
        return listPerfilCliente.stream().map(PerfilClienteDto::new).toList();
    }

    /* Cria um novo perfil de crédito com base nos dados fornecidos */
    public void create(PerfilClienteDto perfilClienteDto) {
        PerfilCliente perfilCliente = new PerfilCliente(perfilClienteDto);
        perfilClienteRepository.save(perfilCliente);
    }

    /*
     * Atualiza os dados de um perfil de crédito existente com base nos dados
     * fornecidos
     */
    public PerfilClienteDto update(PerfilClienteDto perfilClienteDto) {
        PerfilCliente perfilCliente = new PerfilCliente(perfilClienteDto);
        return new PerfilClienteDto(perfilClienteRepository.save(perfilCliente));
    }

    /* Remove um perfil de crédito de acordo com o id */
    public void delete(Integer id) {
        Optional<PerfilCliente> perfilClienteOpt = perfilClienteRepository.findById(id);
        perfilClienteOpt.ifPresent(perfilClienteRepository::delete);
    }

    // Define o perfil do cliente baseado em suas informações
    public void definirPerfilCliente(ClienteReadDto clienteReadDto) {
        Cliente cliente = clienteRepository.findById(clienteReadDto.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Double score = calcularScore(cliente);
        Double percentualRisco = calcularPercentualRisco(cliente);
        String nomePerfil = "Perfil baseado no score"; // Defina a lógica para o nome do perfil

        PerfilCliente perfilCliente = new PerfilCliente();
        perfilCliente.setNomePerfil(nomePerfil);
        perfilCliente.setScore(score);
        perfilCliente.setPercentualRisco(percentualRisco);

        perfilCliente = perfilClienteRepository.save(perfilCliente);

        cliente.setPerfilCliente(perfilCliente);
        clienteRepository.save(cliente);
    }

    private Double calcularScore(Cliente cliente) {
        // Implemente a lógica de cálculo de score com base na renda e outras
        // informações
        return 0.0; // Exemplo
    }

    private Double calcularPercentualRisco(Cliente cliente) {
        // Implemente a lógica de cálculo de risco com base na renda e outras
        // informações
        return 0.0; // Exemplo
    }
}
