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

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoResultadoReadDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoResultado;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoRequisicaoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoResultadoRepository;
import com.analisedecredito.aplicacao_analise_credito.utils.CriaPdf;
import com.analisedecredito.aplicacao_analise_credito.utils.CriaPdfGeral;
import com.itextpdf.text.DocumentException;

@Service
public class EmprestimoResultadoService {

    @Autowired
    EmprestimoResultadoRepository repository;

    @Autowired
    EmprestimoRequisicaoRepository requisicaoRepository;

    @Autowired
    CriaPdf utils;

    @Autowired
    CriaPdfGeral utilsGeral;

    /* Retorna um resultado de empréstimo de acordo com o id */
    public EmprestimoResultadoReadDto findById(Integer id) {
        return new EmprestimoResultadoReadDto(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resultado de empréstimo não encontrado com o ID: " + id)));
    }

    /* Retorna uma lista de resultados de empréstimos cadastrados */
    public List<EmprestimoResultadoReadDto> list() {
        List<EmprestimoResultado> listaResultado = repository.findAll();
        return listaResultado.stream().map(EmprestimoResultadoReadDto::new).toList();
    }

    /* Retorna um pdf com base no id do resultado do empréstimo */
    public ByteArrayOutputStream geraPdfCpf(String cpf, Integer id)
            throws DocumentException, MalformedURLException, IOException {
        List<EmprestimoResultado> resultados = repository.findByClienteCpf(cpf);

        if (resultados.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum resultado de empréstimo encontrado para o CPF: " + cpf);
        }

        EmprestimoResultado resultado = resultados.stream()
                .filter(r -> r.getIdResultado().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nenhum resultado de empréstimo encontrado para o ID: " + id));

        EmprestimoResultadoReadDto dto = new EmprestimoResultadoReadDto(resultado);

        return utils.criaPdfImprimir(dto);
    }

    /* Gera um PDF com base em um período de datas */
    public ByteArrayOutputStream geraPdfPorPeriodo(Date inicio, Date fim)
            throws DocumentException, MalformedURLException, IOException {
        List<EmprestimoResultado> resultados = repository.findByDataCriacao(inicio, fim);

        if (resultados.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum resultado de empréstimo encontrado no período: " +
                    new SimpleDateFormat("yyyy-MM-dd").format(inicio) + " a " +
                    new SimpleDateFormat("yyyy-MM-dd").format(fim));
        }

        List<EmprestimoResultadoReadDto> dtos = resultados.stream()
                .map(EmprestimoResultadoReadDto::new)
                .collect(Collectors.toList());

        return utilsGeral.criaPdfPeriodo(dtos);
    }

    /* Cria um novo resultado de empréstimo com base nos dados fornecidos */
    public void create(EmprestimoResultadoDto emprestimoResultadoDto) {
        EmprestimoResultado emprestimoResultado = new EmprestimoResultado();
        Optional<EmprestimoRequisicao> requisicaoOpt = requisicaoRepository
                .findById(emprestimoResultadoDto.getEmprestimoRequisicao());

        emprestimoResultado.setIdResultado(emprestimoResultadoDto.getIdResultado());
        emprestimoResultado.setAprovado(emprestimoResultadoDto.getAprovado());
        emprestimoResultado.setDescricaoResultado(emprestimoResultadoDto.getDescricaoResultado());
        emprestimoResultado.setDataResultado(emprestimoResultadoDto.getDataResultado());
        emprestimoResultado.setEmprestimoRequisicao(requisicaoOpt
                .orElseThrow(() -> new ResourceNotFoundException("Requisição de empréstimo não encontrada com o ID: "
                        + emprestimoResultadoDto.getEmprestimoRequisicao())));

        repository.save(emprestimoResultado);
    }

    /* Atualiza os dados de um resultado de empréstimo existente */
    public EmprestimoResultadoDto update(Integer id, EmprestimoResultadoDto emprestimoResultadoDto) {
        EmprestimoResultado emprestimoResultado = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resultado de empréstimo não encontrado com o ID: " + id));
        EmprestimoRequisicao requisicao = requisicaoRepository
                .findById(emprestimoResultadoDto.getEmprestimoRequisicao())
                .orElseThrow(() -> new ResourceNotFoundException("Requisição de empréstimo não encontrada com o ID: "
                        + emprestimoResultadoDto.getEmprestimoRequisicao()));

        emprestimoResultado.setIdResultado(emprestimoResultadoDto.getIdResultado());
        emprestimoResultado.setAprovado(emprestimoResultadoDto.getAprovado());
        emprestimoResultado.setDescricaoResultado(emprestimoResultadoDto.getDescricaoResultado());
        emprestimoResultado.setDataResultado(emprestimoResultadoDto.getDataResultado());
        emprestimoResultado.setEmprestimoRequisicao(requisicao);

        EmprestimoResultado updated = repository.save(emprestimoResultado);
        return new EmprestimoResultadoDto(updated);
    }

    /* Remove um dado de resultado de empréstimo pelo id */
    public void delete(Integer id) {
        EmprestimoResultado emprestimoResultado = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resultado de empréstimo não encontrado com o ID: " + id));
        repository.delete(emprestimoResultado);
    }
}
