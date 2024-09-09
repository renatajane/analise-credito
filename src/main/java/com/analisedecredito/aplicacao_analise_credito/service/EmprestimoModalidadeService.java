package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.EmprestimoModalidadeDto;
import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoModalidade;
import com.analisedecredito.aplicacao_analise_credito.repository.EmprestimoModalidadeRepository;

@Service
public class EmprestimoModalidadeService {

    @Autowired
    EmprestimoModalidadeRepository repository;
    
    /* Retorna uma modalidade de empréstimo de acordo com o id */
    public EmprestimoModalidadeDto findById(Integer id){
        return new EmprestimoModalidadeDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todas as modalidades de empréstimos cadastrados */
    public List<EmprestimoModalidadeDto> list(){
        List<EmprestimoModalidade> emprestimoModalidades = repository.findAll();
        return emprestimoModalidades.stream().map(EmprestimoModalidadeDto::new).toList();
    }

    /* Cria um novo tipo de modalidade de empréstimo com base nos dados fornecidos */
    public void create(EmprestimoModalidadeDto emprestimoModalideDto){
        EmprestimoModalidade emprestimoModalidade = new EmprestimoModalidade(emprestimoModalideDto);
        repository.save(emprestimoModalidade);
    }

    /* Atualiza os dados de modalidade de empréstimo existente com base nos dados fornecidos */
    public EmprestimoModalidadeDto update(EmprestimoModalidadeDto emprestimoModalidadeDto){
        EmprestimoModalidade emprestimoModalidade = new EmprestimoModalidade(emprestimoModalidadeDto);
        return new EmprestimoModalidadeDto(repository.save(emprestimoModalidade));
    }

    /* Remove um tipo de modalidade de empréstimo de acordo com o id */
    public void delete(Integer id){
        EmprestimoModalidade emprestimoModalidade = repository.findById(id).get();
        repository.delete(emprestimoModalidade);

    }
}
