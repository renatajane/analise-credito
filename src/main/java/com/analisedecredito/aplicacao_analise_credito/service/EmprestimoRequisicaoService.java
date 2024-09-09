package com.analisedecredito.aplicacao_analise_credito.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    ClienteService clienteService;

    @Autowired
    CalcularValorAprovadoService calculaValorAprovadoService;

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

            Cliente cliente = clienteOpt.get();
            // Verifica se o cliente já possui algum empréstimo em aberto
            // List<EmprestimoRequisicao> emprestimosEmAberto = repository
            // .findRequisicaoByIdClienteAndStatus(cliente.getIdCliente(), true);

            // boolean clienteTemEmprestimoEmAberto = !emprestimosEmAberto.isEmpty();

            // if (clienteTemEmprestimoEmAberto) {
            // throw new IllegalStateException("O cliente já possui um empréstimo em aberto.
            // Não é possível criar uma nova requisição.");
            // }

            EmprestimoRequisicao emprestimoRequisicao = new EmprestimoRequisicao();

            emprestimoRequisicao.setIdRequisicao(emprestimoRequisicaoDto.getIdRequisicao());
            emprestimoRequisicao.setValorRequerido(emprestimoRequisicaoDto.getValorRequerido());
            emprestimoRequisicao.setDataRequisicao(new Date());
            emprestimoRequisicao.setCliente(cliente);
            emprestimoRequisicao.setIof(iof);
            emprestimoRequisicao.setPrazoMes(emprestimoRequisicaoDto.getPrazoMes());
            emprestimoRequisicao.setModalidadePagamento(pagamentoOpt.get());
            emprestimoRequisicao.setEmprestimoModalidade(modalidadeOpt.get());
            emprestimoRequisicao.setEmprestimoObjetivo(objetivoOpt.get());
            emprestimoRequisicao.setEmprestimoUrgencia(urgenciaOpt.get());
            emprestimoRequisicao.setDiaPagamento(emprestimoRequisicaoDto.getDiaPagamento());
            emprestimoRequisicao.setJuros(juros);
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

            Double valorRendaCliente = rendaFonteRepository
                    .findRendaTotalCliente(cliente.getIdCliente());

            // Soma o valor das parcelas de todas as requisições anteriores
            Double despesaCliente = despesaRepository.findDespesaTotalCliente(cliente.getIdCliente());

            // Recupera todas as requisições de empréstimo anteriores do cliente
            List<EmprestimoRequisicao> requisicoesAprovadas = repository
                    .findRequisicaoByIdClienteAndAprovado(cliente.getIdCliente());

            // Soma o valor das parcelas de todas as requisições anteriores
            for (EmprestimoRequisicao requisicao : requisicoesAprovadas) {
                despesaCliente += requisicao.getValorParcela();
            }

            // Adiciona a parcela da nova requisição
            despesaCliente += valorParcela;

            // Arredonda para 2 casas decimais usando BigDecimal
            // BigDecimal despesaClienteRounded = new BigDecimal(despesaCliente).setScale(2,
            // RoundingMode.HALF_UP);

            // Formata o valor como moeda brasileira
            // NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            // String despesaFormatada = nf.format(despesaClienteRounded.doubleValue());

            // Inicializa a descrição e a aprovação
            emprestimoRequisicao.setDescricaoResultado("");
            emprestimoRequisicao.setAprovado(true); // Assume que o empréstimo é aprovado a princípio

            // 1. Verifica se o valor requerido é maior que a renda
            // if (emprestimoRequisicao.getValorRequerido() > valorRendaCliente) {
            // emprestimoRequisicao.setAprovado(false);
            // emprestimoRequisicao.setDescricaoResultado(
            // "Não é possível solicitar um empréstimo com valor superior à sua renda.");
            // }

            // 2. Verifica se o valor da parcela ultrapassa 30% da renda
            if (valorParcela > (valorRendaCliente * 0.30)) {
                emprestimoRequisicao.setAprovado(false);
                emprestimoRequisicao.setDescricaoResultado("O valor da parcela excede 30% da sua renda.");
            }

            else if (emprestimoRequisicao.getValorRequerido() > cliente.getValorMaximoPreAprovado()) {
                emprestimoRequisicao.setAprovado(false);
                emprestimoRequisicao
                        .setDescricaoResultado("O valor requerido é superior ao valor pré-aprovado para empréstimo.");
            }

            // Caso o empréstimo seja aprovado
            if (emprestimoRequisicao.getAprovado()) {
                emprestimoRequisicao
                        .setDescricaoResultado("Seu perfil atende aos requisitos, por isso o empréstimo foi aprovado.");
            }

            // Salva o emprestimoRequisicao com valores calculados
            repository.save(emprestimoRequisicao);

            // Atualiza o perfil do cliente após a nova requisição
            clienteService.definePerfilCliente(emprestimoRequisicao.getCliente().getIdCliente());

            calculaValorAprovadoService.calculaValorPreAprovado(emprestimoRequisicao.getCliente().getIdCliente());
            repository.save(emprestimoRequisicao);

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

                // Atualiza o perfil do cliente após a nova requisição
                clienteService.definePerfilCliente(emprestimoRequisicao.getCliente().getIdCliente());

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

    /* Retorna uma lista de requisições de empréstimos por CPF do cliente */
    public List<EmprestimoRequisicaoReadDto> listPorCpf(String cpf) {
        // Remove pontuações do CPF
        String cpfNumerico = cpf.replaceAll("\\D", "");

        // Valida se o CPF possui 11 dígitos
        if (cpfNumerico.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. O CPF deve conter 11 dígitos.");
        }

        // Busca as requisições de empréstimo do cliente
        List<EmprestimoRequisicao> listaEmprestimo = repository.findByClienteCpf(cpfNumerico);

        // Converte para DTO
        return listaEmprestimo.stream()
                .map(EmprestimoRequisicaoReadDto::new)
                .collect(Collectors.toList());
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

    /* Gera um PDF com base em um CPF e um período de datas */
    public ByteArrayOutputStream geraPdfPorCpfEPeriodo(String cpf, Date inicio, Date fim)
            throws DocumentException, IOException {
        fim = ajustarFimDoDia(fim);
        List<EmprestimoRequisicao> resultados = repository.findByCpfAndDataRequisicao(cpf, inicio, fim);

        if (resultados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Nenhum resultado de empréstimo encontrado para o CPF " +
                            cpf + " no período: " +
                            new SimpleDateFormat("yyyy-MM-dd").format(inicio) + " a " +
                            new SimpleDateFormat("yyyy-MM-dd").format(fim));
        }

        List<EmprestimoRequisicaoReadDto> dtos = resultados.stream()
                .map(EmprestimoRequisicaoReadDto::new)
                .collect(Collectors.toList());

        return utilsGeral.criaPdfPorPeriodo(dtos, inicio, fim);
    }

    private Date ajustarFimDoDia(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
}
