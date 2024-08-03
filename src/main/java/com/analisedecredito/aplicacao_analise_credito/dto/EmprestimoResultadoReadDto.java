// package com.analisedecredito.aplicacao_analise_credito.dto;

// import java.util.Date;

// import org.springframework.beans.BeanUtils;

// import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoRequisicao;
// import com.analisedecredito.aplicacao_analise_credito.model.EmprestimoResultado;

// public class EmprestimoResultadoReadDto {

//     // Propriedades
//     private Integer idResultado;
//     private EmprestimoRequisicao emprestimoRequisicao;
//     private Boolean aprovado;
//     private String descricaoResultado;
//     private Date dataResultado;

//     // Construtor
//     public EmprestimoResultadoReadDto() {
//     }

//     public EmprestimoResultadoReadDto(EmprestimoResultado emprestimoResultado) {
//         BeanUtils.copyProperties(emprestimoResultado, this);
//         this.emprestimoRequisicao = emprestimoResultado.getEmprestimoRequisicao();
//     }

//     // Getters e Setters  
//     public Integer getIdResultado() {
//         return idResultado;
//     }

//     public Date getDataResultado() {
//         return dataResultado;
//     }

//     public void setDataResultado(Date dataResultado) {
//         this.dataResultado = dataResultado;
//     }

//     public void setIdResultado(Integer idResultado) {
//         this.idResultado = idResultado;
//     }

//     public EmprestimoRequisicao getEmprestimoRequisicao() {
//         return emprestimoRequisicao;
//     }

//     public void setEmprestimoRequisicao(EmprestimoRequisicao emprestimoRequisicao) {
//         this.emprestimoRequisicao = emprestimoRequisicao;
//     }

//     public Boolean getAprovado() {
//         return aprovado;
//     }

//     public void setAprovado(Boolean aprovado) {
//         this.aprovado = aprovado;
//     }

//     public String getDescricaoResultado() {
//         return descricaoResultado;
//     }h

//     public void setDescricaoResultado(String descricaoResultado) {
//         this.descricaoResultado = descricaoResultado;
//     }
// }
