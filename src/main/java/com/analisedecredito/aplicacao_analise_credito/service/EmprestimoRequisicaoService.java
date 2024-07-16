package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoReadDto;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoModalidade;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoObjetivo;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoUrgencia;
import com.analisedecredito.aplicacao_analise_credito.model.IofAtual;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoModalidadeRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoObjetivoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoRequisicaoRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoUrgenciaRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.IofAtualRepository;

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

        Optional<Cliente> clienteOpt = clienteRepository.findById(emprestimoRequisicaoDto.getCliente());
        Optional<IofAtual> iofOpt = iofAtualRepository.findById(emprestimoRequisicaoDto.getIof());
        Optional<EmprestimoModalidade> modalidadeOpt = modalidadeRepository
                .findById(emprestimoRequisicaoDto.getEmprestimoModalidade());
        Optional<EmprestimoObjetivo> objetivoOpt = objetivoRepository
                .findById(emprestimoRequisicaoDto.getEmprestimoObjetivo());
        Optional<EmprestimoUrgencia> urgenciaOpt = urgenciaRepository
                .findById(emprestimoRequisicaoDto.getEmprestimoUrgencia());
        // modalide objetivo urgencia
        if (clienteOpt.isPresent()) {

            EmprestimoRequisicao emprestimoRequisicao = new EmprestimoRequisicao();

            emprestimoRequisicao.setIdRequisicao(emprestimoRequisicaoDto.getIdRequisicao());
            emprestimoRequisicao.setValorRequerido(emprestimoRequisicaoDto.getValorRequerido());
            emprestimoRequisicao.setDataRequisicao(emprestimoRequisicaoDto.getDataRequisicao());
            emprestimoRequisicao.setCliente(clienteOpt.get());
            emprestimoRequisicao.setIof(iofOpt.get());
            emprestimoRequisicao.setEmprestimoModalidade(modalidadeOpt.get());
            emprestimoRequisicao.setEmprestimoObjetivo(objetivoOpt.get());
            emprestimoRequisicao.setEmprestimoUrgencia(urgenciaOpt.get());

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

            Optional<Cliente> clienteOpt = clienteRepository.findById(emprestimoRequisicaoDto.getCliente());
            Optional<IofAtual> iofOpt = iofAtualRepository.findById(emprestimoRequisicaoDto.getIof());
            Optional<EmprestimoModalidade> modalidadeOpt = modalidadeRepository
                    .findById(emprestimoRequisicaoDto.getEmprestimoModalidade());
            Optional<EmprestimoObjetivo> objetivoOpt = objetivoRepository
                    .findById(emprestimoRequisicaoDto.getEmprestimoObjetivo());
            Optional<EmprestimoUrgencia> urgenciaOpt = urgenciaRepository
                    .findById(emprestimoRequisicaoDto.getEmprestimoUrgencia());

            if (clienteOpt.isPresent() && iofOpt.isPresent() 
                && modalidadeOpt.isPresent() && objetivoOpt.isPresent()
                    && urgenciaOpt.isPresent()) {
                emprestimoRequisicao.setIdRequisicao(emprestimoRequisicaoDto.getIdRequisicao());
                emprestimoRequisicao.setValorRequerido(emprestimoRequisicaoDto.getValorRequerido());
                emprestimoRequisicao.setDataRequisicao(emprestimoRequisicaoDto.getDataRequisicao());
                emprestimoRequisicao.setCliente(clienteOpt.get());
                emprestimoRequisicao.setIof(iofOpt.get());
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
    public void delete(Integer id){
        EmprestimoRequisicao emprestimoRequisicao = repository.findById(id).get();
        repository.delete(emprestimoRequisicao);
    }
    
}
