export interface ClienteCompletoDto {
    idCliente: number
    nome: string
    cpf: string
    dataNascimento: string
    email: string
    telefone: string
    endereco: string
    autorizacaoLGPD: boolean
    dataAutorizacaoLGPD: string
    renda: Renda[]
    patrimonio: Patrimonio[]
    despesa: Despesa[]
  }
  
  export interface Renda {
    idRendaFonte: number
    cliente: Cliente
    rendaTipo: RendaTipo
    valorRenda: number
  }
  
  export interface Cliente {
    idCliente: number
    nome: string
    cpf: string
    dataNascimento: string
    email: string
    telefone: string
    endereco: string
    autorizacaoLGPD: boolean
    dataAutorizacaoLGPD: string
    perfilCliente: PerfilCliente
    spcSerasa: boolean
    valorMaximoPreAprovado: number
  }
  
  export interface PerfilCliente {
    idPerfilCliente: number
    nomePerfil: string
    score: number
    percentualRisco: number
  }
  
  export interface RendaTipo {
    idRendaTipo: number
    descricaoRendaTipo: string
  }
  
  export interface Patrimonio {
    idPatrimonio: number
    cliente: Cliente2
    patrimonioTipo: PatrimonioTipo
    valorPatrimonio: number
  }
  
  export interface Cliente2 {
    idCliente: number
    nome: string
    cpf: string
    dataNascimento: string
    email: string
    telefone: string
    endereco: string
    autorizacaoLGPD: boolean
    dataAutorizacaoLGPD: string
    perfilCliente: PerfilCliente2
    spcSerasa: boolean
    valorMaximoPreAprovado: number
  }
  
  export interface PerfilCliente2 {
    idPerfilCliente: number
    nomePerfil: string
    score: number
    percentualRisco: number
  }
  
  export interface PatrimonioTipo {
    idPatrimonioTipo: number
    descricaoPatrimonioTipo: string
  }
  
  export interface Despesa {
    idDespesa: number
    cliente: Cliente3
    despesaTipo: DespesaTipo
    valorDespesa: number
  }
  
  export interface Cliente3 {
    idCliente: number
    nome: string
    cpf: string
    dataNascimento: string
    email: string
    telefone: string
    endereco: string
    autorizacaoLGPD: boolean
    dataAutorizacaoLGPD: string
    perfilCliente: PerfilCliente3
    spcSerasa: boolean
    valorMaximoPreAprovado: number
  }
  
  export interface PerfilCliente3 {
    idPerfilCliente: number
    nomePerfil: string
    score: number
    percentualRisco: number
  }
  
  export interface DespesaTipo {
    idDespesaTipo: number
    descricaoDespesaTipo: string
  }
  