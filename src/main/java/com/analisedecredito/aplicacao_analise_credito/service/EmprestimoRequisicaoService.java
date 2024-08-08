package com.analisedecredito.aplicacao_analise_credito.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import com.analisedecredito.aplicacao_analise_credito.repository.DespesaRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoModalidadeRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoObjetivoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoRequisicaoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoUrgenciaRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.IofAtualRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.JurosRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.ModalidadePagamentoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.PatrimonioRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaFonteRepository;
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

    @Autowired
    PatrimonioRepository patrimonioRepository;

    @Autowired
    RendaFonteRepository rendaFonteRepository;

    @Autowired
    DespesaRepository despesaRepository;

    /* Retorna uma requisição de empréstimo de acordo com o id */
    public EmprestimoRequisicaoReadDto findById(Integer id) {
        return new EmprestimoRequisicaoReadDto(repository.findById(id).get());
    }

    /* Retorna uma lista requisições de empréstimos cadastrados */
    public List<EmprestimoRequisicaoReadDto> list() {
        List<EmprestimoRequisicao> listaEmprestimo = repository.findAll();
        return listaEmprestimo.stream().map(EmprestimoRequisicaoReadDto::new).toList();
    }

    public void create(EmprestimoRequisicaoDto emprestimoRequisicaoDto) {

        Optional<Cliente> clienteOpt = clienteRepository.findById(emprestimoRequisicaoDto.getCliente());
        Optional<EmprestimoModalidade> modalidadeOpt = modalidadeRepository
                .findById(emprestimoRequisicaoDto.getEmprestimoModalidade());
        Optional<EmprestimoObjetivo> objetivoOpt = objetivoRepository
                .findById(emprestimoRequisicaoDto.getEmprestimoObjetivo());
        Optional<EmprestimoUrgencia> urgenciaOpt = urgenciaRepository
                .findById(emprestimoRequisicaoDto.getEmprestimoUrgencia());
        Optional<ModalidadePagamento> pagamentoOpt = pagamentoRepository
                .findById(emprestimoRequisicaoDto.getModalidadePagamento());
        Juros juros = jurosRepository.findByDataJuros(new Date());
        IofAtual iof = iofAtualRepository.findByDataIof(new Date());

        if (clienteOpt.isPresent() && modalidadeOpt.isPresent() && objetivoOpt.isPresent() &&
                urgenciaOpt.isPresent() && pagamentoOpt.isPresent()) {

            EmprestimoRequisicao emprestimoRequisicao = new EmprestimoRequisicao();

            emprestimoRequisicao.setIdRequisicao(emprestimoRequisicaoDto.getIdRequisicao());
            emprestimoRequisicao.setValorRequerido(emprestimoRequisicaoDto.getValorRequerido());
            emprestimoRequisicao.setDataRequisicao(new Date());
            emprestimoRequisicao.setCliente(clienteOpt.get());
            emprestimoRequisicao.setIof(iof);
            emprestimoRequisicao.setPrazoMes(emprestimoRequisicaoDto.getPrazoMes());
            emprestimoRequisicao.setModalidadePagamento(pagamentoOpt.get());
            emprestimoRequisicao.setEmprestimoModalidade(modalidadeOpt.get());
            emprestimoRequisicao.setEmprestimoObjetivo(objetivoOpt.get());
            emprestimoRequisicao.setEmprestimoUrgencia(urgenciaOpt.get());
            emprestimoRequisicao.setDiaPagamento(emprestimoRequisicaoDto.getDiaPagamento());
            emprestimoRequisicao.setJuros(juros);
            emprestimoRequisicao.setDescricaoResultado(emprestimoRequisicaoDto.getDescricaoResultado());
            emprestimoRequisicao.setDataResultado(new Date());

            // Calcula os juros
            double jurosCalculado = calculaJuros(juros.getTaxaJurosMensal(), emprestimoRequisicaoDto.getPrazoMes(),
                    emprestimoRequisicaoDto.getValorRequerido());
            emprestimoRequisicao.setJurosCalculado(jurosCalculado);

            // Calcula o IOF
            double iofCalculado = calculaIof(emprestimoRequisicaoDto, iof);
            emprestimoRequisicao.setIofCalculado(iofCalculado);

            // Calcula o valor total do empréstimo
            double valorTotal = calculaValorTotal(emprestimoRequisicaoDto.getValorRequerido(), iofCalculado,
                    jurosCalculado);
            emprestimoRequisicao.setValorTotal(valorTotal);

            // Calcula o valor da parcela
            Double valorParcela = calculaValorParcela(emprestimoRequisicaoDto.getValorRequerido(), iofCalculado,
                    jurosCalculado, emprestimoRequisicaoDto.getPrazoMes());
            emprestimoRequisicao.setValorParcela(valorParcela);

            // Verifica o patrimônio do cliente
            Double valorPatrimonioCliente = patrimonioRepository
                    .findPatrimonioTotalCliente(clienteOpt.get().getIdCliente());

            Double valorRendaCliente = rendaFonteRepository
                    .findRendaTotalCliente(clienteOpt.get().getIdCliente());

            Double despesaCliente = despesaRepository.findDespesaTotalCliente(clienteOpt.get().getIdCliente());

            // emprestimoRequisicaoDto.getRendaTotal
            System.out.println("minha renda +++++" + valorRendaCliente);
            System.out.println("meu patrimonio +++++" + valorPatrimonioCliente);
            System.out.println("minha despesa +++++" + despesaCliente);

            var perfilCliente = clienteOpt.get().getPerfilCliente().getNomePerfil();

            if (valorRendaCliente > despesaCliente) {
                emprestimoRequisicao.setAprovado(true);
            } else {
                emprestimoRequisicao.setAprovado(false);

            }
            if (valorParcela > (valorRendaCliente * 0.30)) {
                emprestimoRequisicao.setAprovado(false);
            }

            if (emprestimoRequisicao.getAprovado() == true) {
                emprestimoRequisicao
                        .setDescricaoResultado("Seu perfil atende aos requisitos, por isso o empréstimo foi aprovado.");
            } else {
                emprestimoRequisicao
                        .setDescricaoResultado("Não autorizado pelo fato do valor de despesar ser maior que a renda.");
            }

            if (perfilCliente.contains("Perfil de Baixo Risco")) {
                System.out.println("Arrasou!");
            }
            System.out.println("perfil do meu cliente ******" + clienteOpt.get().getPerfilCliente().getNomePerfil());

            // Salva o emprestimoRequisicao com valores calculados
            repository.save(emprestimoRequisicao);

            // Verifica o patrimônio do cliente
            // Double valorPatrimonioCliente = patrimonioRepository
            // .findPatrimonioTotalCliente(clienteOpt.get().getIdCliente());

            System.out.println("meu patrimonio +++++" + valorPatrimonioCliente);

            // Decide se o empréstimo é aprovado com base no patrimônio do cliente
            // emprestimoRequisicaoDto.setAprovado(valorPatrimonioCliente >
            // emprestimoRequisicaoDto.getValorTotal());
            System.out.println("emprestimo requisicao +++ " + emprestimoRequisicaoDto.getAprovado());
        } else {
            throw new ResourceNotFoundException("Dados necessários não encontrados.");
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
    public Double calculaJuros(Double juros, Integer prazoEmMeses, Double valorRequerido) {

        double calculoTotal = valorRequerido * juros * prazoEmMeses;

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
    public Double calculaValorTotal(Double valorRequerido, Double iofCalculado, Double jurosCalculado) {
        return valorRequerido + iofCalculado + jurosCalculado;
    }

    /* Calcula valor da parcela */
    public Double calculaValorParcela(Double valorRequerido, Double iofCalculado,
            Double jurosCalculado, Integer prazoEmMes) {

        var soma = valorRequerido + iofCalculado + jurosCalculado;
        var mensalidade = soma / prazoEmMes;

        // Arredondar para duas casas decimais
        BigDecimal mensalidadeArredondada = new BigDecimal(mensalidade).setScale(2, RoundingMode.HALF_UP);

        return mensalidadeArredondada.doubleValue();
    }

    public String formataValorParaReal(Double valor) {
        // Formatar o valor para Real (BRL)
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return currencyFormatter.format(valor);
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
