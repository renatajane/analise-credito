package com.analisedecredito.aplicacao_analise_credito.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analisedecredito.aplicacao_analise_credito.backend.dto.PatrimonioDto;
import com.analisedecredito.aplicacao_analise_credito.backend.dto.PatrimonioReadDto;
import com.analisedecredito.aplicacao_analise_credito.backend.exception.ResourceNotFoundException;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.backend.model.Patrimonio;
import com.analisedecredito.aplicacao_analise_credito.backend.model.PatrimonioTipo;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.ClienteRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.PatrimonioRepository;
import com.analisedecredito.aplicacao_analise_credito.backend.repository.PatrimonioTipoRepository;

@Service
public class PatrimonioService {

    @Autowired
    PatrimonioRepository repository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PatrimonioTipoRepository tipoRepository;

    @Autowired
    PatrimonioTipoRepository patrimonioTipoRepository;

    /* Retorna um patrimônio de acordo com o id */
    public PatrimonioReadDto findById(Integer id) {
        return new PatrimonioReadDto(repository.findById(id).get());
    }

    /* Retorna um patrimônio cadastrado */
    public List<PatrimonioReadDto> list() {
        List<Patrimonio> listPatrimonio = repository.findAll();
        return listPatrimonio.stream().map(PatrimonioReadDto::new).toList();
    }

    /* Retorna um patrimônio de acordo com o id do cliente */
    public List<PatrimonioReadDto> findByIdCliente(Integer id) {
        List<Patrimonio> listaPatrimonio = repository.findByIdCliente(id);
        return listaPatrimonio.stream().map(PatrimonioReadDto::new).toList();
    }

    /* Cria um novo patrimônio com base nos dados fornecidos */
    public void create(PatrimonioDto patrimonioDto) {

        Optional<Cliente> clienteOpt = clienteRepository.findById(patrimonioDto.getCliente());
        Optional<PatrimonioTipo> tipoOpt = tipoRepository.findById(patrimonioDto.getPatrimonioTipo());

        if (clienteOpt.isPresent()) {
            Patrimonio patrimonio = new Patrimonio();

            patrimonio.setIdPatrimonio(patrimonioDto.getIdPatrimonio());
            patrimonio.setValorPatrimonio(patrimonioDto.getValorPatrimonio());
            patrimonio.setCliente(clienteOpt.get());
            patrimonio.setPatrimonioTipo(tipoOpt.get());

            repository.save(patrimonio);
        } else {
            throw new ResourceNotFoundException(
                    "Cliente não encontrado com id " + patrimonioDto.getCliente());
        }
    }

    /* Atualiza o patrimonio com base nos dados fornecidos */
    public void update(Integer id, PatrimonioDto patrimonioDto) {

        // Busca o patrimônio existente pelo ID fornecido
        Optional<Patrimonio> patrimonioOpt = repository.findById(id);

        // Verifica se o patrimônio existe
        if (patrimonioOpt.isPresent()) {
            Patrimonio patrimonio = patrimonioOpt.get();

            // Busca o cliente e o tipo de patrimônio associados ao DTO
            Optional<Cliente> clienteOpt = clienteRepository.findById(patrimonioDto.getCliente());
            Optional<PatrimonioTipo> patrimonioTipoOpt = patrimonioTipoRepository
                    .findById(patrimonioDto.getPatrimonioTipo());

            // Verifica se o cliente e o tipo de patrimônio existem
            if (clienteOpt.isPresent() && patrimonioTipoOpt.isPresent()) {
                // Atualiza os campos do patrimônio com os dados fornecidos
                Cliente cliente = clienteOpt.get();
                PatrimonioTipo patrimonioTipo = patrimonioTipoOpt.get();

                patrimonio.setValorPatrimonio(patrimonioDto.getValorPatrimonio());
                patrimonio.setCliente(cliente);
                patrimonio.setPatrimonioTipo(patrimonioTipo);

                // Salva as alterações no banco de dados
                repository.save(patrimonio);
            } else {
                throw new ResourceNotFoundException("Cliente ou Tipo de Patrimônio não encontrado.");
            }
        } else {
            throw new ResourceNotFoundException("Patrimônio não encontrado com id " + id);
        }

    }

    /* Remove o patrimônio com base no id */
    public void delete(Integer id) {
        Optional<Patrimonio> patrimonioOpt = repository.findById(id);

        if (patrimonioOpt.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Patrimônio não encontrado com id " + id);
        }
    }

}
