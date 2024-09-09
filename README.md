# Sistema de Análise de Crédito

Este repositório contém os componentes do sistema de Análise de Crédito, projetado para permitir a gestão de clientes e requisições de empréstimo. A API fornece funcionalidades para o cadastro de clientes, processamento de requisições de empréstimo e análise de crédito.

## Visão Geral

O sistema de Análise de Crédito é desenvolvido para gerenciar informações relacionadas a clientes e empréstimos. Este sistema é composto pelos seguintes serviços:

- **Gestão de Clientes**: Cadastro e consulta de informações de clientes.
- **Gestão de Requisições de Empréstimo**: Criação e consulta de requisições de empréstimo.
- **Análise de Crédito**: Avaliação e aprovação de solicitações de empréstimo com base nos dados do cliente.

Cada serviço é independente, possui sua própria infraestrutura e se comunica com os demais por meio de APIs.

## Serviços

### 1. Gestão de Clientes

Permite o cadastro, atualização e consulta de clientes.

- **Endpoints Principais**:
  - `POST /clientes`: Cadastra um novo cliente.
  - `GET /cliente/completo/{cpf}`: Consulta os dados de um cliente pelo CPF.

### 2. Gestão de Requisições de Empréstimo

Gerencia as requisições de empréstimo, incluindo criação e consulta.

- **Endpoints Principais**:
  - `POST /emprestimo-requisicao`: Cria uma nova requisição de empréstimo.
  - `GET /emprestimo-requisicao/list-by-cpf`: Consulta as requisições de empréstimo de um cliente pelo CPF.

### 3. Análise de Crédito

Realiza a análise e aprovação de empréstimos com base em dados financeiros e perfil de crédito.

- **Endpoints Principais**:
  - `POST /analise-credito`: Executa a análise de crédito para uma requisição de empréstimo.

## Linguagens e Ferramentas Utilizadas

- **Java**: Para o desenvolvimento do backend com Spring Boot.
- **React**: Para o desenvolvimento do frontend.
- **PostgreSQL**: Banco de dados relacional para armazenamento de dados.
- **Docker**: Para containerização e gerenciamento de ambientes.
- **Swagger**: Para documentação e teste das APIs.
- **Vite**: Ferramenta de build para desenvolvimento frontend.
- **CSS3** e **HTML5**: Para estilização e estruturação do frontend.
- **JavaScript**: Para lógica de frontend e integração com APIs.

## Configuração e Execução

1. Clone o repositório:
    ```bash
    git clone https://github.com/renatajane/analise-credito.git
    ```

2. Navegue até o diretório do projeto:
    ```bash
    cd analise-credito
    ```

3. Configure o banco de dados e outros serviços conforme necessário. 
   - Faça alterações nos dados do arquivo `.env` e insira suas informações de senha e login para o banco de dados, e para as configurações do Keycloak.

### 3.1 Configuração do Keycloak

1. Acesse a interface do Keycloak em `http://localhost:8088`.
2. Faça login usando as credenciais definidas no arquivo `.env`.
3. Crie um **Realm** para o sistema de análise de crédito.
4. Crie um **Cliente** dentro do Realm e atribua o valor gerado para `Client secret` à variável `client_secret` no arquivo `Login.jsx`.
5. Crie um **Usuário** dentro do Realm, incluindo um atributo personalizado chamado `cpf` e o valor correspondente.

4. Inicie o backend:
    ```bash
    ./mvnw spring-boot:run
    ```

5. Inicie o frontend:
    ```bash
    npm run dev
    ```

6. Acesse a API em `http://localhost:8080` e o frontend em `http://localhost:5173`.

### 7. Acesse o sistema

Após a configuração do Keycloak, você pode realizar o login na interface de análise de crédito usando as credenciais de login e senha criadas para o usuário.

## Documentação

Para visualizar a documentação da API gerada pela especificação OpenAPI, você pode acessar `http://localhost:8080/swagger-ui.html`.

## Contribuição

Sinta-se à vontade para contribuir com melhorias ou correções. Crie uma issue ou envie um pull request com suas modificações.

---

**Autor:** Renata Souza

**Data:** Setembro de 2024
