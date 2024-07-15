package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoDto;
import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoRequisicaoReadDto;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoRequisicaoRepository;

@Service
public class EmprestimoRequisicaoService {
    
    @Autowired
    EmprestimoRequisicaoRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    /* Retorna uma requisição de empréstimo de acordo com o id */
    public EmprestimoRequisicaoReadDto findById(Integer id){
        return new EmprestimoRequisicaoReadDto(repository.findById(id).get());
    }

    /* Retorna uma lista requisições de empréstimos cadastrados */
    public List<EmprestimoRequisicaoReadDto> list(){
        List<EmprestimoRequisicao> listaEmprestimo = repository.findAll();
        return listaEmprestimo.stream().map(EmprestimoRequisicaoReadDto::new).toList();
    }

    /* Cria uma nova requisição de empréstimo com base nos dados fornecidos */
    public void create(EmprestimoRequisicaoDto emprestimoRequisicaoDto){

        Optional<Cliente> clienteOpt = clienteRepository.findById(emprestimoRequisicaoDto.getCliente());
        if (clienteOpt.isPresent()) {
        
            EmprestimoRequisicao emprestimoRequisicao = new EmprestimoRequisicao();
            
            emprestimoRequisicao.setIdRequisicao(emprestimoRequisicaoDto.getIdRequisicao());
            emprestimoRequisicao.setValorRequerido(emprestimoRequisicaoDto.getValorRequerido());
            emprestimoRequisicao.setDataRequisicao(emprestimoRequisicaoDto.getDataRequisicao());
            emprestimoRequisicao.setCliente(clienteOpt.get());

            // Setar os valores corretos
            emprestimoRequisicao.setIof(null);
            emprestimoRequisicao.setEmprestimoModalidade(null);
            emprestimoRequisicao.setEmprestimoObjetivo(null);
            emprestimoRequisicao.setEmprestimoUrgencia(null);
    
            repository.save(emprestimoRequisicao);
        } else {
            throw new ResourceNotFoundException(
                    "Cliente não encontrado com id " + emprestimoRequisicaoDto.getCliente());
        }
    }

}
