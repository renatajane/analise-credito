package com.analisedecredito.aplicacao_analise_credito.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.BeneficiadoDto;
import com.analisedecredito.aplicacao_analise_credito.backend.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.backend.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.DespesaRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.DespesaTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.EmprestimoRequisicaoRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.PatrimonioRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.PatrimonioTipoRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.RendaFonteRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.RendaTipoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CalcularValorAprovadoService {

    @Autowired
    ClienteRepository repository;

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

    public void calculaValorPreAprovado(Integer idCliente) {

        Optional<Cliente> clienteOpt = repository.findById(idCliente);

        if (!clienteOpt.isPresent()) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        Cliente cliente = clienteOpt.get();
        Double rendaTotal = somaRenda(idCliente);
        // Double despesa = calculaDespesaTotal(idCliente);
        Double valorMaximoPreAprovado;

        // Se o valor preaprovado ainda não foi calculado, faça o cálculo inicial
        valorMaximoPreAprovado = 0.0;
        // String perfilCliente = cliente.getPerfilCliente().getNomePerfil();

        // if (perfilCliente.contains("Perfil de Baixo Risco")) {
            valorMaximoPreAprovado = rendaTotal * 3;
        // } else if (perfilCliente.contains("Perfil de Risco Moderado")) {
        //     valorMaximoPreAprovado = rendaTotal * 2;
        // } else if (perfilCliente.contains("Perfil de Alto Risco")) {
        //     valorMaximoPreAprovado = rendaTotal * 1.5;
        // }

        // Busca a última requisição de empréstimo aprovada do cliente
        List<EmprestimoRequisicao> requisicoesAprovadas = requisicaoRepository
                .findRequisicaoByIdClienteAndAprovado(cliente.getIdCliente());

            if (requisicoesAprovadas != null && !requisicoesAprovadas.isEmpty()) {
                for (EmprestimoRequisicao emprestimo : requisicoesAprovadas) {
                    Double valorRequerido = emprestimo.getValorRequerido();
                    valorMaximoPreAprovado -= valorRequerido;
                }
            }

        // Atualiza o valor preaprovado no cliente
        cliente.setValorMaximoPreAprovado(valorMaximoPreAprovado);

        // Certifique-se de que o valor preaprovado não seja negativo
        if (cliente.getValorMaximoPreAprovado() < 0) {
            cliente.setValorMaximoPreAprovado(0.0);
        }

        repository.save(cliente);
    }

    public BeneficiadoDto processarBeneficiado(String cpf) {
        BeneficiadoDto beneficiado = beneficiadoService.buscaBeneficiado(cpf);

        if (beneficiado != null) {
            return beneficiado;
        }
        return new BeneficiadoDto(cpf, null); // Retorna o CPF e valor null se não encontrado
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

            return rendaTotal;
        } else {
            // Cliente não encontrado, lançar exceção ou retornar valor padrão
            throw new EntityNotFoundException("Cliente não encontrado para o ID: " + id);
        }
    }

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
}
