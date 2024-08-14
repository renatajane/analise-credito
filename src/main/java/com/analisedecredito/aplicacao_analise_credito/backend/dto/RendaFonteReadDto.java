package com.analisedecredito.aplicacao_analise_credito.backend.dto;

import org.springframework.beans.BeanUtils;

import com.analisedecredito.aplicacao_analise_credito.backend.model.Cliente;
import com.analisedecredito.aplicacao_analise_credito.backend.model.RendaFonte;
import com.analisedecredito.aplicacao_analise_credito.backend.model.RendaTipo;

public class RendaFonteReadDto {

        // Propriedades
        private Integer idRendaFonte;
        private Cliente cliente;
        private RendaTipo rendaTipo;
        private Double valorRenda;

        // Construtor
        public RendaFonteReadDto() {
        }

        public RendaFonteReadDto(RendaFonte rendaFonte) {
            BeanUtils.copyProperties(rendaFonte, this);
        }

        // Getters e Setters
        public Integer getIdRendaFonte() {
            return idRendaFonte;
        }
        public void setIdRendaFonte(Integer idRendaFonte) {
            this.idRendaFonte = idRendaFonte;
        }
        public Cliente getCliente() {
            return cliente;
        }
        public void setCliente(Cliente cliente) {
            this.cliente = cliente;
        }
        public RendaTipo getRendaTipo() {
            return rendaTipo;
        }
        public void setRendaTipo(RendaTipo rendaTipo) {
            this.rendaTipo = rendaTipo;
        }
        public Double getValorRenda() {
            return valorRenda;
        }
        public void setValorRenda(Double valorRenda) {
            this.valorRenda = valorRenda;
        }
           
}
