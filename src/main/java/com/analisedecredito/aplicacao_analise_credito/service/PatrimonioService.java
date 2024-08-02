package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.PatrimonioDto;
import com.analisedecredito.aplicacao_analise_credito.dto.PatrimonioReadDto;
import com.analisedecredito.aplicacao_analise_credito.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.Patrimonio;
import com.analisedecredito.aplicacao_analise_credito.model.PatrimonioTipo;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.PatrimonioRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.PatrimonioTipoRepository;

@Service
public class PatrimonioService {

    @Autowired
    PatrimonioRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PatrimonioTipoRepository tipoRepository;

    /* Retorna um patrimônio de acordo com o id */
    public PatrimonioReadDto findById(Integer id){
        return new PatrimonioReadDto(repository.findById(id).get());
    }

    /* Retorna um patrimônio cadastrado */
    public List<PatrimonioReadDto> list(){
        List<Patrimonio> listPatrimonio = repository.findAll();
        return listPatrimonio.stream().map(PatrimonioReadDto::new).toList();
    }

    /* Cria um novo patrimônio com base nos dados fornecidos */
    public void create(PatrimonioDto patrimonioDto){

        Optional<Cliente> clienteOpt = clienteRepository.findById(patrimonioDto.getCliente());
        Optional<PatrimonioTipo> tipoOpt = tipoRepository.findById(patrimonioDto.getPatrimonioTipo());

        if(clienteOpt.isPresent()){
        Patrimonio patrimonio = new Patrimonio();

        patrimonio.setIdPatrimonio(patrimonioDto.getIdPatrimonio());
        patrimonio.setValorPatrimonio(patrimonioDto.getValorPatrimonio());
        patrimonio.setCliente(clienteOpt.get());
        patrimonio.setPatrimonioTipo(tipoOpt.get());

        repository.save(patrimonio);
        } else {
            throw new ResourceNotFoundException (
                    "Cliente não encontrado com id " + patrimonioDto.getCliente());
        }
    }
    
}
