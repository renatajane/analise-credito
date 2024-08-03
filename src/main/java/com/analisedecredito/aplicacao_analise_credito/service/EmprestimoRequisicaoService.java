package com.analisedecredito.aplicacao_analise_credito.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoReadDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoModalidade;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoObjetivo;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoUrgencia;
import com.analisedecredito.aplicacao_analise_credito.model.IofAtual;
import com.analisedecredito.aplicacao_analise_credito.model.Juros;
import com.analisedecredito.aplicacao_analise_credito.model.ModalidadePagamento;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoModalidadeRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoObjetivoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoRequisicaoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoUrgenciaRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.IofAtualRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.JurosRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.ModalidadePagamentoRepository;
import com.analisedecredito.aplicacao_analise_credito.utils.CriaPdf;
import com.analisedecredito.aplicacao_analise_credito.utils.CriaPdfGeral;
import com.itextpdf.text.DocumentException;

@Service
public class EmprestimoRequisicaoService {

    @Autowired
    EmprestimoRequisicaoRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    IofAtualRepository iofAtualRepository;

    @Autowired
    EmprestimoModalidadeRepository modalidadeRepository;

    @Autowired
    EmprestimoObjetivoRepository objetivoRepository;

    @Autowired
    EmprestimoUrgenciaRepository urgenciaRepository;

    @Autowired
    ModalidadePagamentoRepository pagamentoRepository;

    @Autowired
    JurosRepository jurosRepository;

    @Autowired
    CriaPdf utils;

    @Autowired
    CriaPdfGeral utilsGeral;

    /* Retorna uma requisição de empréstimo de acordo com o id */
    public EmprestimoRequisicaoReadDto findById(Integer id) {
        return new EmprestimoRequisicaoReadDto(repository.findById(id).get());
    }

    /* Retorna uma lista requisições de empréstimos cadastrados */
    public List<EmprestimoRequisicaoReadDto> list() {
        List<EmprestimoRequisicao> listaEmprestimo = repository.findAll();
        return listaEmprestimo.stream().map(EmprestimoRequisicaoReadDto::new).toList();
    }

    /* Cria uma nova requisição de empréstimo com base nos dados fornecidos */
    public void create(EmprestimoRequisicaoDto emprestimoRequisicaoDto) {

        Optional<Cliente> clienteOpt = clienteRepository
                .findById(emprestimoRequisicaoDto.getCliente());
        Optional<IofAtual> iofOpt = iofAtualRepository
                .findById(emprestimoRequisicaoDto.getIof());
        Optional<EmprestimoModalidade> modalidadeOpt = modalidadeRepository
                .findById(emprestimoRequisicaoDto.getEmprestimoModalidade());
        Optional<EmprestimoObjetivo> objetivoOpt = objetivoRepository
                .findById(emprestimoRequisicaoDto.getEmprestimoObjetivo());
        Optional<EmprestimoUrgencia> urgenciaOpt = urgenciaRepository
                .findById(emprestimoRequisicaoDto.getEmprestimoUrgencia());
        Optional<ModalidadePagamento> pagamentoOpt = pagamentoRepository
                .findById(emprestimoRequisicaoDto.getModalidadePagamento());
        Juros juros = jurosRepository.findByDataJuros(new Date());

        if (clienteOpt.isPresent()) {

            EmprestimoRequisicao emprestimoRequisicao = new EmprestimoRequisicao();

            emprestimoRequisicao.setIdRequisicao(emprestimoRequisicaoDto.getIdRequisicao());
            emprestimoRequisicao.setValorRequerido(emprestimoRequisicaoDto.getValorRequerido());
            emprestimoRequisicao.setDataRequisicao(new Date());
            emprestimoRequisicao.setCliente(clienteOpt.get());
            emprestimoRequisicao.setIof(iofOpt.get());
            emprestimoRequisicao.setPrazoMes(emprestimoRequisicaoDto.getPrazoMes());
            emprestimoRequisicao.setModalidadePagamento(pagamentoOpt.get());
            emprestimoRequisicao.setEmprestimoModalidade(modalidadeOpt.get());
            emprestimoRequisicao.setEmprestimoObjetivo(objetivoOpt.get());
            emprestimoRequisicao.setEmprestimoUrgencia(urgenciaOpt.get());
            emprestimoRequisicao.setDiaPagamento(emprestimoRequisicaoDto.getDiaPagamento());
            emprestimoRequisicao.setJuros(juros);
            emprestimoRequisicao.setAprovado(emprestimoRequisicaoDto.getAprovado());
            emprestimoRequisicao.setDescricaoResultado(emprestimoRequisicaoDto.getDescricaoResultado());
            emprestimoRequisicao.setDataResultado(emprestimoRequisicaoDto.getDataResultado());
            emprestimoRequisicao.setJurosCalculado(calculaJuros(juros, emprestimoRequisicaoDto.getPrazoMes(),
            emprestimoRequisicaoDto.getValorRequerido()));
            emprestimoRequisicao.setIofCalculado(calculaIof(emprestimoRequisicaoDto, iofOpt.get()));
            emprestimoRequisicao.setValorTotal(calculaValorTotal(emprestimoRequisicaoDto));
            emprestimoRequisicao.setValorParcela(calculaValorParcela(emprestimoRequisicaoDto));

            repository.save(emprestimoRequisicao);
        } else {
            throw new ResourceNotFoundException(
                    "Cliente não encontrado com id " + emprestimoRequisicaoDto.getCliente());
        }
    }

    /* Atualiza os dados de uma requisição de empréstimo existente */
    public EmprestimoRequisicaoDto update(Integer id, EmprestimoRequisicaoDto emprestimoRequisicaoDto) {
        Optional<EmprestimoRequisicao> requisicaoOpt = repository.findById(id);

        if (requisicaoOpt.isPresent()) {
            EmprestimoRequisicao emprestimoRequisicao = requisicaoOpt.get();

            Optional<Cliente> clienteOpt = clienteRepository
                    .findById(emprestimoRequisicaoDto.getCliente());
            Optional<IofAtual> iofOpt = iofAtualRepository
                    .findById(emprestimoRequisicaoDto.getIof());
            Optional<EmprestimoModalidade> modalidadeOpt = modalidadeRepository
                    .findById(emprestimoRequisicaoDto.getEmprestimoModalidade());
            Optional<EmprestimoObjetivo> objetivoOpt = objetivoRepository
                    .findById(emprestimoRequisicaoDto.getEmprestimoObjetivo());
            Optional<EmprestimoUrgencia> urgenciaOpt = urgenciaRepository
                    .findById(emprestimoRequisicaoDto.getEmprestimoUrgencia());
            Optional<ModalidadePagamento> pagamentoOpt = pagamentoRepository
                    .findById(emprestimoRequisicaoDto.getModalidadePagamento());

            if (clienteOpt.isPresent() && iofOpt.isPresent()
                    && modalidadeOpt.isPresent() && objetivoOpt.isPresent()
                    && urgenciaOpt.isPresent()) {
                emprestimoRequisicao.setIdRequisicao(emprestimoRequisicaoDto.getIdRequisicao());
                emprestimoRequisicao.setValorRequerido(emprestimoRequisicaoDto.getValorRequerido());
                emprestimoRequisicao.setDataRequisicao(emprestimoRequisicaoDto.getDataRequisicao());
                emprestimoRequisicao.setCliente(clienteOpt.get());
                emprestimoRequisicao.setIof(iofOpt.get());
                emprestimoRequisicao.setModalidadePagamento(pagamentoOpt.get());
                emprestimoRequisicao.setEmprestimoModalidade(modalidadeOpt.get());
                emprestimoRequisicao.setEmprestimoObjetivo(objetivoOpt.get());
                emprestimoRequisicao.setEmprestimoUrgencia(urgenciaOpt.get());

                EmprestimoRequisicao update = repository.save(emprestimoRequisicao);

                return new EmprestimoRequisicaoDto(update);
            } else {
                throw new ResourceNotFoundException("Algum dos recursos associados não foi encontrado.");
            }
        } else {
            throw new ResourceNotFoundException("Perfil de crédito não encontrado com id " + id);
        }
    }

    /* Remove uma requisição de empréstimo pelo id */
    public void delete(Integer id) {
        EmprestimoRequisicao emprestimoRequisicao = repository.findById(id).get();
        repository.delete(emprestimoRequisicao);
    }

    /* Calcula juros da requisição */
    public Double calculaJuros(Juros juros, int prazoEmMeses, double valorRequerido) {

        double taxaMensal = juros.getTaxaJurosMensal();
        double calculoTotal = valorRequerido * taxaMensal * prazoEmMeses;

        return calculoTotal;
    }

    /* Calcula iof da requisição */
    public Double calculaIof(EmprestimoRequisicaoDto requisicaoDto, IofAtual iof) {

        double valorRequerido = requisicaoDto.getValorRequerido();
        double taxaIof = iof.getTaxaIof();
        double iofTotal = valorRequerido * taxaIof;
        return iofTotal;
    }

    /* Calcula valor total da requisição */
    public Double calculaValorTotal(EmprestimoRequisicaoDto dto) {
        double valorRequerido = dto.getValorRequerido();
        double iof = dto.getIofCalculado();
        double juros = dto.getJurosCalculado();
        return iof + juros + valorRequerido;

    }

    /* Calcula valor da parcela */
    public Double calculaValorParcela(EmprestimoRequisicaoDto dto) {

        var valorRequerido = dto.getValorRequerido();
        var prazoPagamento = dto.getPrazoMes();
        var iof = dto.getIofCalculado();
        var juros = dto.getJurosCalculado();
        var soma = valorRequerido + iof + juros;
        var mensalidade = soma / prazoPagamento;
        return mensalidade;
    }

    /* Retorna um pdf com base no id do resultado do empréstimo */
    public ByteArrayOutputStream geraPdfCpf(String cpf, Integer id)
            throws DocumentException, MalformedURLException, IOException {
        List<EmprestimoRequisicao> requisicoes = repository.findByClienteCpf(cpf);

        if (requisicoes.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum resultado de empréstimo encontrado para o CPF: " + cpf);
        }

        EmprestimoRequisicao requisicao = requisicoes.stream()
                .filter(r -> r.getIdRequisicao().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nenhum resultado de empréstimo encontrado para o ID: " + id));

        EmprestimoRequisicaoReadDto dto = new EmprestimoRequisicaoReadDto(requisicao);

        return utils.criaPdfImprimir(dto);
    }

    /* Gera um PDF com base em um período de datas */
    public ByteArrayOutputStream geraPdfPorPeriodo(Date inicio, Date fim)
            throws DocumentException, MalformedURLException, IOException {
        List<EmprestimoRequisicao> resultados = repository.findByDataCriacao(inicio, fim);

        if (resultados.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum resultado de empréstimo encontrado no período: " +
                    new SimpleDateFormat("yyyy-MM-dd").format(inicio) + " a " +
                    new SimpleDateFormat("yyyy-MM-dd").format(fim));
        }

        List<EmprestimoRequisicaoReadDto> dtos = resultados.stream()
                .map(EmprestimoRequisicaoReadDto::new)
                .collect(Collectors.toList());

        return utilsGeral.criaPdfPeriodo(dtos);
    }

}
