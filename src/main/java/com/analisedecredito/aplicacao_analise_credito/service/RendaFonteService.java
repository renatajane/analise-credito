package com.analisedecredito.aplicacao_analise_credito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.dto.RendaFonteDto;
import com.analisedecredito.aplicacao_analise_credito.dto.RendaFonteReadDto;
import com.analisedecredito.aplicacao_analise_credito.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.model.RendaFonte;
import com.analisedecredito.aplicacao_analise_credito.model.RendaTipo;
import com.analisedecredito.aplicacao_analise_credito.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaFonteRepository;
import com.analisedecredito.aplicacao_analise_credito.repository.RendaTipoRepository;

@Service
public class RendaFonteService {

    @Autowired
    RendaFonteRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    RendaTipoRepository rendaTipoRepository;

    @Autowired
    ClienteService clienteService;

    /* Retorna uma fonte de renda de acordo com o id */
    public RendaFonteReadDto findById(Integer id){
        return new RendaFonteReadDto(repository.findById(id).get());
    }

    /* Retorna uma lista de todas as fontes de renda cadastradas */
    public List<RendaFonteReadDto> list(){
        List<RendaFonte> listRendaFonte = repository.findAll();
        return listRendaFonte.stream().map(RendaFonteReadDto::new).toList();
    }
    
    /* Cria uma nova fonte de renda com base nos dados fornecidos */
    public void create(RendaFonteDto rendaFonteDto){

        Optional<Cliente> clienteOpt = clienteRepository.findById(rendaFonteDto.getCliente());
        Optional<RendaTipo> rendaTipoOpt = rendaTipoRepository.findById(rendaFonteDto.getRendaTipo());

        RendaFonte rendaFonte = new RendaFonte();

        rendaFonte.setIdRendaFonte(rendaFonteDto.getIdRendaFonte());
        rendaFonte.setCliente(clienteOpt.get());
        rendaFonte.setRendaTipo(rendaTipoOpt.get());
        rendaFonte.setValorRenda(rendaFonteDto.getValorRenda());

        repository.save(rendaFonte);

        clienteService.definePerfilCliente(clienteOpt.get().getIdCliente());
        var a = clienteService.calculaValorPreAprovado(clienteOpt.get().getIdCliente());
        System.out.println("MEU VALOR PRE APROVADO" + a);
    }

    /* Atualiza os dados de uma fonte de renda existente */
    public RendaFonteDto update(Integer id, RendaFonteDto rendaFonteDto){
        Optional<RendaFonte> rendaFonteOpt = repository.findById(id);

        RendaFonte rendaFonte = rendaFonteOpt.get();

        Optional<Cliente> clienteOpt = clienteRepository.findById(rendaFonteDto.getCliente());
        Optional<RendaTipo> rendaTipoOpt = rendaTipoRepository.findById(rendaFonteDto.getRendaTipo());

        rendaFonte.setIdRendaFonte(rendaFonteDto.getIdRendaFonte());
        rendaFonte.setCliente(clienteOpt.get());
        rendaFonte.setRendaTipo(rendaTipoOpt.get());
        rendaFonte.setValorRenda(rendaFonteDto.getValorRenda());

        RendaFonte updated = repository.save(rendaFonte);
        clienteService.definePerfilCliente(clienteOpt.get().getIdCliente());
        var a = clienteService.calculaValorPreAprovado(clienteOpt.get().getIdCliente());
        System.out.println("MEU VALOR PRE APROVADO" + a);
        return new RendaFonteDto(updated);
    }

    /* Remove um dado de fonte de renda pelo id */
    public void delete(Integer id){
        RendaFonte rendaFonte = repository.findById(id).get();
        repository.delete(rendaFonte);
        clienteService.definePerfilCliente(rendaFonte.getCliente().getIdCliente());
    }

}
