package com.analisedecredito.aplicacao_analise_credito.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.BeneficiadoDto;
import com.analisedecredito.aplicacao_analise_credito.backend.dto.ClienteCompletoReadDto;
import com.analisedecredito.aplicacao_analise_credito.backend.dto.ClienteDto;
import com.analisedecredito.aplicacao_analise_credito.backend.dto.ClienteReadDto;
import com.analisedecredito.aplicacao_analise_credito.backend.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Despesa;
import com.analisedecredito.aplicacao_analise_credito.backend.model.DespesaTipo;
import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Patrimonio;
import com.analisedecredito.aplicacao_analise_credito.backend.model.PatrimonioTipo;
import com.analisedecredito.aplicacao_analise_credito.backend.model.PerfilCliente;
import com.analisedecredito.aplicacao_analise_credito.backend.model.RendaFonte;
import com.analisedecredito.aplicacao_analise_credito.backend.model.RendaTipo;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.DespesaRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.DespesaTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.EmprestimoRequisicaoRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.PatrimonioRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.PatrimonioTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.PerfilClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.RendaFonteRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.RendaTipoRepository;

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
    DespesaTipoRepository despesaTipoRepository;

    @Autowired
    DespesaRepository despesaRepository;

    @Autowired
    private BeneficiadoService beneficiadoService;

    /* Retorna um cliente de acordo com o id */
    public ClienteReadDto findById(Integer id) {
        return new ClienteReadDto(repository.findById(id).get());
    }

    /* Retorna um cliente de acordo com o cpf */
    public ClienteReadDto findByCpf(String cpf) {
        Cliente cliente = repository.findByCpf(cpf).orElse(null);
        if (cliente == null) {
            return null;
        }
        return new ClienteReadDto(cliente);
    }

    /* Retorna os dados completos de um cliente de acordo com o cpf */
    public ClienteCompletoReadDto findByCpfCompleto(String cpf) {

        Cliente cliente = repository.findByCpf(cpf).orElse(null);

        if (cliente == null) {
            return null;
        }
        List<RendaFonte> renda = rendaRepository.findByCpfCliente(cpf);
        List<Patrimonio> patrimonio = patrimonioRepository.findByCpfCliente(cpf);
        List<Despesa> despesa = despesaRepository.findByCpfCliente(cpf);

        return new ClienteCompletoReadDto(
                cliente,
                renda.isEmpty() ? new ArrayList<>() : renda,
                patrimonio.isEmpty() ? new ArrayList<>() : patrimonio,
                despesa.isEmpty() ? new ArrayList<>() : despesa);
    }

    /* Cria o cliente completo com dados pessoais e financeiros */
    @Transactional
    public void createOrUpdateClienteCompleto(ClienteCompletoReadDto clienteCompletoDto) {

        // Verifica se o cliente autoriza o tratamento de dados
        if (!clienteCompletoDto.getAutorizacaoLGPD()) {
            throw new IllegalArgumentException(
                    "O cliente deve autorizar o tratamento de seus dados de acordo com a LGPD.");
        }

        if (!clienteCompletoDto.getRendas().isEmpty()) {
            for (RendaFonte item : clienteCompletoDto.getRendas()) {
                if (item.getValorRenda() <= 0) {
                    throw new IllegalArgumentException(
                            "O valor da renda não pode ser menor ou igual a 0.");
                }
            }
        }

        if (!clienteCompletoDto.getPatrimonios().isEmpty()) {
            for (Patrimonio item : clienteCompletoDto.getPatrimonios()) {
                if (item.getValorPatrimonio() <= 0) {
                    throw new IllegalArgumentException(
                            "O valor do patrimônio não pode ser menor ou igual a 0.");
                }
            }
        }

        if (!clienteCompletoDto.getDespesas().isEmpty()) {
            for (Despesa item : clienteCompletoDto.getDespesas()) {
                if (item.getValorDespesa() <= 0) {
                    throw new IllegalArgumentException(
                            "O valor da despesa não pode ser menor ou igual a 0.");
                }
            }
        }

        // Tenta encontrar o cliente existente pelo CPF
        Optional<Cliente> clienteExistenteOpt = repository.findByCpf(clienteCompletoDto.getCpf());

        Cliente cliente;

        if (clienteExistenteOpt.isPresent()) {
            // Cliente já existe, verifica se os dados são os mesmos
            cliente = clienteExistenteOpt.get();

            // Se os dados forem diferentes, atualiza o cliente existente
            cliente.setNome(clienteCompletoDto.getNome());
            cliente.setDataNascimento(clienteCompletoDto.getDataNascimento());
            cliente.setEmail(clienteCompletoDto.getEmail());
            cliente.setEndereco(clienteCompletoDto.getEndereco());
            cliente.setTelefone(clienteCompletoDto.getTelefone());
            cliente.setSpcSerasa(clienteCompletoDto.getSpcSerasa());
            cliente.setAutorizacaoLGPD(clienteCompletoDto.getAutorizacaoLGPD());
            cliente.setDataAutorizacaoLGPD(new Date());

            // Atualiza o cliente no banco
            cliente = repository.save(cliente);

            List<RendaFonte> listaRendas = rendaRepository.findByIdCliente(cliente.getIdCliente());
            List<Despesa> listaDespesas = despesaRepository.findByIdCliente(cliente.getIdCliente());
            List<Patrimonio> listaPatrimonios = patrimonioRepository.findByIdCliente(cliente.getIdCliente());

            // Remove os dados financeiros antigos associadas ao cliente
            if (!listaRendas.isEmpty()) {
                rendaRepository.deleteByClienteIdCliente(cliente.getIdCliente());
            }
            if (!listaPatrimonios.isEmpty()) {
                patrimonioRepository.deleteByClienteIdCliente(cliente.getIdCliente());
            }
            if (!listaDespesas.isEmpty()) {
                despesaRepository.deleteByClienteIdCliente(cliente.getIdCliente());
            }

        } else {
            // Cliente não existe, cria um novo
            cliente = new Cliente();
            cliente.setNome(clienteCompletoDto.getNome());
            cliente.setCpf(clienteCompletoDto.getCpf());
            cliente.setAutorizacaoLGPD(clienteCompletoDto.getAutorizacaoLGPD());
            cliente.setDataAutorizacaoLGPD(new Date());
            cliente.setDataNascimento(clienteCompletoDto.getDataNascimento());
            cliente.setEmail(clienteCompletoDto.getEmail());
            cliente.setEndereco(clienteCompletoDto.getEndereco());
            cliente.setTelefone(clienteCompletoDto.getTelefone());
            cliente.setSpcSerasa(clienteCompletoDto.getSpcSerasa());

            cliente = repository.save(cliente);
        }

        // Adiciona as novas rendas se existirem
        if (!clienteCompletoDto.getRendas().isEmpty()) {
            for (RendaFonte item : clienteCompletoDto.getRendas()) {
                Optional<RendaTipo> rendaTipoOpt = rendaTipoRepository.findById(item.getRendaTipo().getIdRendaTipo());
                if (rendaTipoOpt.isPresent()) {
                    RendaFonte renda = new RendaFonte();
                    renda.setCliente(cliente);
                    renda.setRendaTipo(rendaTipoOpt.get());
                    renda.setValorRenda(item.getValorRenda());
                    rendaRepository.save(renda);
                } else {
                    throw new IllegalArgumentException(
                            "Tipo de renda não encontrado: " + item.getRendaTipo().getIdRendaTipo());
                }
            }
        }

        // Adiciona os novos patrimônios se existirem
        if (!clienteCompletoDto.getPatrimonios().isEmpty()) {
            for (Patrimonio item : clienteCompletoDto.getPatrimonios()) {
                Optional<PatrimonioTipo> patrimonioTipoOpt = patrimonioTipoRepository
                        .findById(item.getPatrimonioTipo().getIdPatrimonioTipo());
                if (patrimonioTipoOpt.isPresent()) {
                    Patrimonio patrimonio = new Patrimonio();
                    patrimonio.setCliente(cliente);
                    patrimonio.setPatrimonioTipo(patrimonioTipoOpt.get());
                    patrimonio.setValorPatrimonio(item.getValorPatrimonio());
                    patrimonioRepository.save(patrimonio);
                } else {
                    throw new IllegalArgumentException(
                            "Tipo de renda não encontrado: " + item.getPatrimonioTipo().getIdPatrimonioTipo());
                }
            }
        }

        // Adiciona as novas despesas se existirem
        if (!clienteCompletoDto.getDespesas().isEmpty()) {
            for (Despesa item : clienteCompletoDto.getDespesas()) {
                Optional<DespesaTipo> despesaTipoOpt = despesaTipoRepository
                        .findById(item.getDespesaTipo().getIdDespesaTipo());
                if (despesaTipoOpt.isPresent()) {
                    Despesa despesa = new Despesa();
                    despesa.setCliente(cliente);
                    despesa.setDespesaTipo(despesaTipoOpt.get());
                    despesa.setValorDespesa(item.getValorDespesa());
                    despesaRepository.save(despesa);
                } else {
                    throw new IllegalArgumentException(
                            "Tipo de renda não encontrado: " + item.getDespesaTipo().getIdDespesaTipo());
                }
            }
        }
        // Define o perfil do cliente e calcula o valor pré-aprovado para empréstimo
        definePerfilCliente(cliente.getIdCliente());
        cliente = repository.save(cliente);

        var valorPreAprovado = calculaValorPreAprovado(cliente.getIdCliente());
        cliente.setValorMaximoPreAprovado(valorPreAprovado);
        cliente = repository.save(cliente);
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
        cliente.setSpcSerasa(clienteDto.getSpcSerasa());

        cliente = repository.save(cliente);

        // Processa o beneficiado sem interromper o fluxo
        definePerfilCliente(cliente.getIdCliente());
        calculaValorPreAprovado(cliente.getIdCliente());
        BeneficiadoDto info = processarBeneficiado(clienteDto.getCpf());
        var preAprovado = calculaValorPreAprovado(cliente.getIdCliente());
        System.out.println("meu valor aprovadoooo +++++" + preAprovado);
        System.out.println("meu beneficiado CPF +++++" + info.getCpf());
        System.out.println("meu beneficiado Valor +++++" + info.getValorBeneficio());

        cliente = repository.save(cliente);
        repository.save(cliente);

        // if (clienteDto.getListaRenda() != null &&
        // !clienteDto.getListaRenda().isEmpty()) {
        // for (RendaFonteDto item : clienteDto.getListaRenda()) {
        // Optional<RendaTipo> rendaTipoOpt =
        // rendaTipoRepository.findById(item.getIdRendaFonte());
        // RendaFonte renda = new RendaFonte(item, cliente, rendaTipoOpt.get());
        // rendaRepository.save(renda);
        // }
        // }

        // if (clienteDto.getListaPatrimonio() != null &&
        // !clienteDto.getListaPatrimonio().isEmpty()) {
        // for (PatrimonioDto item : clienteDto.getListaPatrimonio()) {
        // Optional<PatrimonioTipo> patrimonioTipoOpt =
        // patrimonioTipoRepository.findById(item.getIdPatrimonio());
        // Patrimonio patrimonio = new Patrimonio(item, cliente,
        // patrimonioTipoOpt.get());
        // patrimonioRepository.save(patrimonio);
        // }
        // }

        // if (clienteDto.getListaDespesa() != null &&
        // !clienteDto.getListaDespesa().isEmpty()) {
        // for (DespesaDto item : clienteDto.getListaDespesa()) {
        // Optional<DespesaTipo> despesaTipoOpt =
        // despesaTipoRepository.findById(item.getIdDespesa());
        // Despesa despesa = new Despesa(item, cliente, despesaTipoOpt.get());
        // despesaRepository.save(despesa);
        // }
        // }

        // Determinando o perfil do cliente baseado na lógica de negócio

        // Atualizando o cliente com o perfil
        // cliente = repository.save(cliente);
    }

    public Double calculaValorPreAprovado(Integer idCliente) {

        Optional<Cliente> clienteOpt = repository.findById(idCliente);
        if (!clienteOpt.isPresent()) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        Cliente cliente = clienteOpt.get();
        Double rendaTotal = somaRenda(idCliente);
        Double despesa = calculaDespesaTotal(idCliente);
        Double valorMaximoPreAprovado = 0.0;
        var perfilCliente = cliente.getPerfilCliente().getNomePerfil();

        if (perfilCliente.contains("Perfil de Baixo Risco")) {
            valorMaximoPreAprovado = (rendaTotal - despesa) * 3;
        } else if (perfilCliente.contains("Perfil de Risco Moderado")) {
            valorMaximoPreAprovado = (rendaTotal - despesa) * 2;
        } else if (perfilCliente.contains("Perfil de Alto Risco")) {
            valorMaximoPreAprovado = (rendaTotal - despesa) * 1.5;
        }
        if (valorMaximoPreAprovado < 0) {
            valorMaximoPreAprovado = 0.0;
        }

        return valorMaximoPreAprovado;
    }

    public BeneficiadoDto processarBeneficiado(String cpf) {
        BeneficiadoDto beneficiado = beneficiadoService.buscaBeneficiado(cpf);

        if (beneficiado != null) {
            return beneficiado;
        }
        return new BeneficiadoDto(cpf, null); // Retorna o CPF e valor null se não encontrado
    }

    public void definePerfilCliente(Integer idCliente) {

        Optional<Cliente> clienteOpt = repository.findById(idCliente);
        if (!clienteOpt.isPresent()) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        Cliente cliente = clienteOpt.get();
        Double valorDespesa = calculaDespesaTotal(idCliente);
        Double valorRenda = somaRenda(idCliente);

        // Score base inicial
        Integer scoreBase = 700;

        if (cliente.getSpcSerasa()) {
            scoreBase -= 100;
        }
        if (valorDespesa > 0.30 * valorRenda) {
            scoreBase -= 200;
        }
        // if (valorDespesa < 0.20 * valorRenda) {
        // scoreBase += 100;
        // }

        PerfilCliente perfilCliente = perfilClienteRepository.findScore(scoreBase);

        // Certifique-se de que um perfil válido foi retornado
        if (perfilCliente == null) {
            throw new IllegalArgumentException("Perfil de cliente não encontrado para o score: " + scoreBase);
        }

        cliente.setPerfilCliente(perfilCliente);
        repository.save(cliente);

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

                definePerfilCliente(id);

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

            // Verifica se o cliente é beneficiado
            BeneficiadoDto beneficiado = processarBeneficiado(cliente.getCpf());
            if (beneficiado.getValorBeneficio() != null) {
                rendaTotal += beneficiado.getValorBeneficio(); // Adiciona o valor do benefício à renda total
            }

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

    /*
     * Calcula a despesa total do cliente, incluindo todas as despesas e parcelas de
     * empréstimos
     */
    public Double calculaDespesaTotal(Integer idCliente) {

        // Verifica se o cliente existe
        Optional<Cliente> clienteOpt = repository.findById(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado com o id " + idCliente);
        }

        Cliente cliente = clienteOpt.get();

        // Calcula a soma de todas as despesas do cliente
        Double despesaTotal = despesaRepository.findDespesaTotalCliente(cliente.getIdCliente());

        // Verifica se o cliente tem requisições de empréstimo ativo e aprovado
        List<EmprestimoRequisicao> emprestimosAtivos = requisicaoRepository
                .findRequisicaoByIdClienteAndStatus(cliente.getIdCliente(), true);

        // Soma os valores das parcelas das requisições ativas às despesas totais
        if (emprestimosAtivos != null && !emprestimosAtivos.isEmpty()) {
            for (EmprestimoRequisicao emprestimo : emprestimosAtivos) {
                System.out.println("Valor da Parcela: " + emprestimo.getValorParcela());
                despesaTotal += emprestimo.getValorParcela();
            }
        }

        return despesaTotal;
    }

    /* Remove um cliente pelo id */
    public void delete(Integer id) {
        Cliente cliente = repository.findById(id).get();
        repository.delete(cliente);
    }

}
