package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.ClienteDto;
import com.analisedecredito.aplicacao_analise_credito.dto.ClienteReadDto;
import com.analisedecredito.aplicacao_analise_credito.dto.DespesaDto;
import com.analisedecredito.aplicacao_analise_credito.dto.PatrimonioDto;
import com.analisedecredito.aplicacao_analise_credito.dto.RendaFonteDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.model.AnaliseRestricao;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.Despesa;
import com.analisedecredito.aplicacao_analise_credito.model.DespesaTipo;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.model.Patrimonio;
import com.analisedecredito.aplicacao_analise_credito.model.PatrimonioTipo;
import com.analisedecredito.aplicacao_analise_credito.model.PerfilCliente;
import com.analisedecredito.aplicacao_analise_credito.model.RendaFonte;
import com.analisedecredito.aplicacao_analise_credito.model.RendaTipo;
import com.analisedecredito.aplicacao_analise_credito.repository.AnaliseRestricaoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.DespesaRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.DespesaTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoRequisicaoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.PatrimonioRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.PatrimonioTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.PerfilClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaFonteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaTipoRepository;

import jakarta.persistence.EntityNotFoundException;

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

    @Autowired
    RendaTipoRepository rendaTipoRepository;

    @Autowired
    PatrimonioRepository patrimonioRepository;

    @Autowired
    PatrimonioTipoRepository patrimonioTipoRepository;

    @Autowired
    AnaliseRestricaoRepository restricaoRepository;

    @Autowired
    DespesaTipoRepository despesaTipoRepository;

    @Autowired
    DespesaRepository despesaRepository;

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
        cliente.setTelefone(clienteDto.getTelefone());
        cliente = repository.save(cliente);

        if (clienteDto.getListaRenda() != null && !clienteDto.getListaRenda().isEmpty()) {
            for (RendaFonteDto item : clienteDto.getListaRenda()) {
                Optional<RendaTipo> rendaTipoOpt = rendaTipoRepository.findById(item.getIdRendaFonte());
                RendaFonte renda = new RendaFonte(item, cliente, rendaTipoOpt.get());
                rendaRepository.save(renda);
            }
        }

        if (clienteDto.getListaPatrimonio() != null && !clienteDto.getListaPatrimonio().isEmpty()) {
            for (PatrimonioDto item : clienteDto.getListaPatrimonio()) {
                Optional<PatrimonioTipo> patrimonioTipoOpt = patrimonioTipoRepository.findById(item.getIdPatrimonio());
                Patrimonio patrimonio = new Patrimonio(item, cliente, patrimonioTipoOpt.get());
                patrimonioRepository.save(patrimonio);
            }
        }

        if (clienteDto.getListaDespesa() != null && !clienteDto.getListaDespesa().isEmpty()) {
            for (DespesaDto item : clienteDto.getListaDespesa()) {
                Optional<DespesaTipo> despesaTipoOpt = despesaTipoRepository.findById(item.getIdDespesa());
                Despesa despesa = new Despesa(item, cliente, despesaTipoOpt.get());
                despesaRepository.save(despesa);
            }
        }

        // Determinando o perfil do cliente baseado na lógica de negócio
        Double valorPatrimonio = somaPatrimonio(cliente.getIdCliente());
        List<EmprestimoRequisicao> requisicoes = requisicaoRepository.findRequisicao(clienteDto.getIdCliente());

        if (!requisicoes.isEmpty() && requisicoes.get(0).getValorRequerido() > valorPatrimonio) {
            Optional<PerfilCliente> perfilClienteOpt = perfilClienteRepository.findById(clienteDto.getPerfilCliente());

            if (perfilClienteOpt.isPresent()) {
                PerfilCliente perfilCliente = perfilClienteOpt.get();

                // Obtendo informações de análise de restrição para determinar o score
                Optional<AnaliseRestricao> analiseRestricaoOpt = restricaoRepository
                        .findByClienteId(clienteDto.getIdCliente());

                if (analiseRestricaoOpt.isPresent()) {

                    // Lógica para determinar o score baseado no status das restrições
                    double score = calculaScore(clienteDto.getIdCliente());
                    perfilCliente.setScore(score);

                    // Determinando o nome do perfil com base no score ou outras lógicas
                    if (score < 500) {
                        perfilCliente.setNomePerfil("Perfil de Alto Risco");
                    } else if (score < 700) {
                        perfilCliente.setNomePerfil("Perfil de Risco Médio");
                    } else {
                        perfilCliente.setNomePerfil("Perfil de Baixo Risco");
                    }

                    cliente.setPerfilCliente(perfilCliente);
                } else {
                    throw new IllegalArgumentException(
                            "Análise de restrição não encontrada para o cliente ID: " + clienteDto.getIdCliente());
                }
            } else {
                throw new IllegalArgumentException(
                        "Perfil de crédito não encontrado para o ID: " + clienteDto.getPerfilCliente());
            }
        } else {
            // Lógica para caso o valor requerido seja menor que o patrimônio, se necessário
            // Exemplo: definir um perfil padrão ou de menor risco
            PerfilCliente perfilClienteDefault = perfilClienteRepository.findByNomePerfil("Perfil de Baixo Risco")
                    .orElseThrow(
                            () -> new IllegalArgumentException("Perfil padrão não encontrado"));
            cliente.setPerfilCliente(perfilClienteDefault);
        }

        // Atualizando o cliente com o perfil
        cliente = repository.save(cliente);
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

    /* Calcula renda total do cliente */
    public Double somaRenda(Integer id) {

        Optional<Cliente> clienteOptional = repository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Double rendaTotal = rendaRepository.findRendaTotalCliente(cliente.getIdCliente());
            ClienteDto clienteDto = new ClienteDto(cliente);
            clienteDto.setRendaTotal(rendaTotal);

            return clienteDto.getRendaTotal();
        } else {
            // Cliente não encontrado, lançar exceção ou retornar valor padrão
            throw new EntityNotFoundException("Cliente não encontrado para o ID: " + id);
        }
    }

    /* Calcula patrimônio do cliente */
    public Double somaPatrimonio(Integer id) {

        Optional<Cliente> clienteOptional = repository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Double patrimonioTotal = patrimonioRepository.findPatrimonioTotalCliente(cliente.getIdCliente());
            ClienteDto clienteDto = new ClienteDto(cliente);
            clienteDto.setPatrimonioTotal(patrimonioTotal);

            return clienteDto.getPatrimonioTotal();
        }
        return 0.0;
    }

    /* Calcula despesa do cliente */
    public Double somaDespesa(Integer id) {

        Optional<Cliente> clienteOptional = repository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Double despesaTotal = despesaRepository.findDespesaTotalCliente(cliente.getIdCliente());
            ClienteDto clienteDto = new ClienteDto(cliente);
            clienteDto.setDespesaTotal(despesaTotal);

            return clienteDto.getDespesaTotal();
        }

        return 0.0;
    }

    /* Calcula score do cliente */
    public Integer calculaScore(Integer id) {

        Integer scoreBase = 700;

        // Recupera o cliente pelo ID
        Optional<Cliente> clienteOptional = repository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new IllegalArgumentException("Cliente não encontrado para o ID: " + id);
        }

        Cliente cliente = clienteOptional.get();

        // Recupera a análise de restrição associada ao cliente
        Optional<AnaliseRestricao> analiseRestricaoOptional = restricaoRepository
                .findByClienteId(cliente.getIdCliente());
        if (!analiseRestricaoOptional.isPresent()) {
            throw new IllegalArgumentException(
                    "Análise de restrição não encontrada para o cliente ID: " + cliente.getIdCliente());
        }

        AnaliseRestricao analiseRestricao = analiseRestricaoOptional.get();

        // Ajusta o score com base nas informações de restrição
        if (analiseRestricao.getStatusSerasa() != null && analiseRestricao.getStatusSerasa()) {
            scoreBase -= 100;
        }
        if (analiseRestricao.getStatusSpc() != null && analiseRestricao.getStatusSpc()) {
            scoreBase -= 100;
        }

        return scoreBase;
    }

    /* Remove um cliente pelo id */
    public void delete(Integer id) {
        Cliente cliente = repository.findById(id).get();
        repository.delete(cliente);
    }

}
