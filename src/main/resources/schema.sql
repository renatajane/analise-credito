-- Cria schema se não existir
CREATE SCHEMA IF NOT EXISTS db_analise;

SET search_path TO db_analise;

-- Criação da tabela PerfilCliente
CREATE TABLE IF NOT EXISTS perfil_cliente (
    id_perfil_cliente SERIAL PRIMARY KEY,
    nome_perfil VARCHAR(255) NOT NULL,
    score DOUBLE PRECISION NOT NULL,
    percentual_risco DOUBLE PRECISION NOT NULL
);

-- Inserir 'Perfil de Baixo Risco' se não existir
INSERT INTO perfil_cliente (nome_perfil, score, percentual_risco)
SELECT 'Perfil de Baixo Risco', 650, 10
WHERE NOT EXISTS (
    SELECT 1 FROM perfil_cliente WHERE nome_perfil = 'Perfil de Baixo Risco'
);
INSERT INTO perfil_cliente (nome_perfil, score, percentual_risco)
SELECT 'Perfil de Risco Moderado', 700, 25
WHERE NOT EXISTS (
    SELECT 1 FROM perfil_cliente WHERE nome_perfil = 'Perfil de Risco Moderado'
);
INSERT INTO perfil_cliente (nome_perfil, score, percentual_risco)
SELECT 'Perfil de Alto Risco', 750, 40
WHERE NOT EXISTS (
    SELECT 1 FROM perfil_cliente WHERE nome_perfil = 'Perfil de Alto Risco'
);

-- Criação da tabela ModalidadePagamento
CREATE TABLE IF NOT EXISTS modalidade_pagamento (
    id_modalidade_pagamento SERIAL PRIMARY KEY,
    descricao_pagamento VARCHAR(100) NOT NULL
);

INSERT INTO modalidade_pagamento (descricao_pagamento)  
SELECT 'Boleto' WHERE NOT EXISTS(SELECT 1 FROM modalidade_pagamento WHERE descricao_pagamento = 'Boleto');

INSERT INTO modalidade_pagamento (descricao_pagamento)  
SELECT 'Débito em conta' WHERE NOT EXISTS(SELECT 1 FROM modalidade_pagamento WHERE descricao_pagamento = 'Débito em conta');

INSERT INTO modalidade_pagamento (descricao_pagamento)  
SELECT 'Folha de pagamento' WHERE NOT EXISTS(SELECT 1 FROM modalidade_pagamento WHERE descricao_pagamento = 'Folha de pagamento');

-- Criação da tabela Cliente 
CREATE TABLE IF NOT EXISTS cliente (
    id_cliente SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf BIGINT UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    endereco VARCHAR(80) NOT NULL,
    autorizacao_lgpd BOOLEAN NOT NULL, 
    data_autorizacao_lgpd DATE NOT NULL, 
    id_perfil_cliente_fk INTEGER REFERENCES perfil_cliente (id_perfil_cliente) 
);

-- Verifica se a tabela cliente está vazia e insere dados se necessário
INSERT INTO cliente (nome, cpf, data_nascimento, email, telefone, endereco, autorizacao_lgpd, data_autorizacao_lgpd, id_perfil_cliente_fk)
SELECT 'João Silva', 12345655001, '1985-05-12', 'joaos.silva@example.com', '1111122111', 'Rua A, 123', true, '2024-07-12', 1
WHERE NOT EXISTS (SELECT 1 FROM cliente LIMIT 1);

INSERT INTO cliente (nome, cpf, data_nascimento, email, telefone, endereco, autorizacao_lgpd, data_autorizacao_lgpd, id_perfil_cliente_fk)
SELECT 'Maria Oliveira', 23880789012, '1990-06-15', 'marias.oliveira@example.com', '2222200222', 'Rua B, 456', true, '2024-05-12', 2
WHERE NOT EXISTS (SELECT 1 FROM cliente WHERE nome = 'Maria Oliveira');

INSERT INTO cliente (nome, cpf, data_nascimento, email, telefone, endereco, autorizacao_lgpd, data_autorizacao_lgpd, id_perfil_cliente_fk)
SELECT 'Pedro Santos', 39967000123, '1988-07-20', 'pedros.santos@example.com', '3333355333', 'Rua C, 789', true, '2024-07-23', 3
WHERE NOT EXISTS (SELECT 1 FROM cliente WHERE nome = 'Pedro Santos');

-- Criação da tabela de IofAtual
CREATE TABLE IF NOT EXISTS iof_atual (
    id_iof SERIAL PRIMARY KEY,
    taxa_iof DECIMAL(10, 8) NOT NULL,
    data_calculo DATE NOT NULL
);

-- Inserir dados na tabela IofAtual apenas se não existirem registros
INSERT INTO iof_atual (taxa_iof, data_calculo)
SELECT 0.0038, '2024-01-01'
WHERE NOT EXISTS (SELECT 1 FROM iof_atual);


-- Criação da tabela de RendaTipo
CREATE TABLE IF NOT EXISTS renda_tipo (
    id_renda_tipo SERIAL PRIMARY KEY,
    descricao_renda_tipo VARCHAR(100) NOT NULL 
);

-- Inserir dados na tabela renda_tipo apenas se não existirem registros
INSERT INTO renda_tipo (descricao_renda_tipo)
SELECT 'salário'
WHERE NOT EXISTS (SELECT 1 FROM renda_tipo);

INSERT INTO renda_tipo (descricao_renda_tipo)
SELECT 'pensão'
WHERE NOT EXISTS (SELECT 1 FROM renda_tipo WHERE descricao_renda_tipo = 'pensão');

-- Criação da tabela de DespesaTipo
CREATE TABLE IF NOT EXISTS despesa_tipo (
    id_despesa_tipo SERIAL PRIMARY KEY,
    descricao_despesa_tipo VARCHAR(100) NOT NULL 
);

-- Inserir dados na tabela despesa_tipo apenas se não existirem registros
INSERT INTO despesa_tipo (descricao_despesa_tipo)
SELECT 'aluguel'
WHERE NOT EXISTS (SELECT 1 FROM despesa_tipo);

INSERT INTO despesa_tipo (descricao_despesa_tipo)
SELECT 'alimentação'
WHERE NOT EXISTS (SELECT 1 FROM despesa_tipo WHERE descricao_despesa_tipo = 'alimentação');

-- Criação da tabela RendaFonte
CREATE TABLE IF NOT EXISTS renda_fonte (
    id_renda_fonte SERIAL PRIMARY KEY,
    id_cliente_fk INTEGER REFERENCES cliente(id_cliente),
    id_renda_tipo_fk INTEGER REFERENCES renda_tipo(id_renda_tipo),
    valor_renda NUMERIC(15, 2) NOT NULL CHECK (valor_renda > 0)
);

-- Inserir dados na tabela renda_fonte apenas se não existirem registros
INSERT INTO renda_fonte (id_cliente_fk, id_renda_tipo_fk, valor_renda) 
SELECT 1, 1, 5000.00
WHERE NOT EXISTS (SELECT 1 FROM renda_fonte);

-- Criação da tabela Despesa
CREATE TABLE IF NOT EXISTS despesa (
    id_despesa SERIAL PRIMARY KEY,
    id_cliente_fk INTEGER REFERENCES cliente(id_cliente),
    id_despesa_tipo_fk INTEGER REFERENCES despesa_tipo(id_despesa_tipo),
    valor_despesa NUMERIC(15, 2) NOT NULL CHECK (valor_despesa > 0)
);

-- Inserir dados na tabela despesa apenas se não existirem registros
INSERT INTO despesa (id_cliente_fk, id_despesa_tipo_fk, valor_despesa)  
SELECT 1, 1, 1500.00
WHERE NOT EXISTS (SELECT 1 FROM despesa);

-- Criação da tabela EmprestimoModalidade
CREATE TABLE IF NOT EXISTS emprestimo_modalidade (
    id_modalidade SERIAL PRIMARY KEY,
    descricao_modalidade VARCHAR(100) NOT NULL
);

-- Inserir dados na tabela EmprestimoModalidade se não existirem
INSERT INTO emprestimo_modalidade (descricao_modalidade)  
SELECT 'Pessoal' WHERE NOT EXISTS(SELECT 1 FROM emprestimo_modalidade WHERE descricao_modalidade = 'Pessoal')
UNION ALL
SELECT 'Imobiliário' WHERE NOT EXISTS(SELECT 1 FROM emprestimo_modalidade WHERE descricao_modalidade = 'Imobiliário')
UNION ALL
SELECT 'Automóvel' WHERE NOT EXISTS(SELECT 1 FROM emprestimo_modalidade WHERE descricao_modalidade = 'Automóvel');


-- Criação da tabela Emprestimoobjetivo
CREATE TABLE IF NOT EXISTS emprestimo_objetivo (
    id_objetivo SERIAL PRIMARY KEY,
    descricao_objetivo VARCHAR(100) NOT NULL 
);

-- Inserir dados na tabela EmprestimoObjetivo se não existirem
INSERT INTO emprestimo_objetivo (descricao_objetivo)  
SELECT 'Saúde' WHERE NOT EXISTS(SELECT 1 FROM emprestimo_objetivo WHERE descricao_objetivo = 'Saúde')
UNION ALL
SELECT 'Compras' WHERE NOT EXISTS(SELECT 1 FROM emprestimo_objetivo WHERE descricao_objetivo = 'Compras')
UNION ALL
SELECT 'Viagem' WHERE NOT EXISTS(SELECT 1 FROM emprestimo_objetivo WHERE descricao_objetivo = 'Viagem');

-- Criação da tabela EmprestimoUrgencia
CREATE TABLE IF NOT EXISTS emprestimo_urgencia (
    id_urgencia SERIAL PRIMARY KEY,
    prazo_urgencia VARCHAR(100) NOT NULL 
);

-- Inserir dados na tabela EmprestimoUrgencia se não existirem
INSERT INTO emprestimo_urgencia (prazo_urgencia)  
SELECT '30 dias' WHERE NOT EXISTS(SELECT 1 FROM emprestimo_urgencia WHERE prazo_urgencia = '30 dias')
UNION ALL
SELECT 'Imediato' WHERE NOT EXISTS(SELECT 1 FROM emprestimo_urgencia WHERE prazo_urgencia = 'Imediato')
UNION ALL
SELECT '15 dias' WHERE NOT EXISTS(SELECT 1 FROM emprestimo_urgencia WHERE prazo_urgencia = '15 dias');

-- Criação da tabela Juros
CREATE TABLE IF NOT EXISTS juros (
    id_juros SERIAL PRIMARY KEY,
    nome_modalidade VARCHAR(255) NOT NULL,
    taxa_juros_anual NUMERIC(5, 2) NOT NULL,
    taxa_juros_mensal NUMERIC(4, 2) NOT NULL,
    data_vigencia DATE NOT NULL
);

--Inserir dados na tabela juros apenas se não existirem registros
INSERT INTO juros (nome_modalidade, taxa_juros_anual, taxa_juros_mensal, data_vigencia)
SELECT 'Pessoal', 0.15, 0.012, '2024-07-25'
WHERE NOT EXISTS (SELECT 1 FROM juros WHERE nome_modalidade = 'Pessoal' AND data_vigencia = '2024-07-25');

INSERT INTO juros (nome_modalidade, taxa_juros_anual, taxa_juros_mensal, data_vigencia)
SELECT 'Imobiliário', 0.08, 0.0065, '2024-07-25'
WHERE NOT EXISTS (SELECT 1 FROM juros WHERE nome_modalidade = 'Imobiliário' AND data_vigencia = '2024-07-25');

INSERT INTO juros (nome_modalidade, taxa_juros_anual, taxa_juros_mensal, data_vigencia)
SELECT 'Consignado', 0.10, 0.008, '2024-07-25'
WHERE NOT EXISTS (SELECT 1 FROM juros WHERE nome_modalidade = 'Consignado' AND data_vigencia = '2024-07-25');

-- Criação da tabela EmprestimoParcela
CREATE TABLE IF NOT EXISTS emprestimo_parcela (
    id_emprestimo_parcela SERIAL PRIMARY KEY,
    valor_parcela NUMERIC(15, 2) NOT NULL,
    data_vencimento DATE NOT NULL
);

-- Inserir uma parcela com valor e data de vencimento específicos
INSERT INTO emprestimo_parcela (valor_parcela, data_vencimento)
SELECT 150.00, '2024-08-01'
WHERE NOT EXISTS (SELECT 1 FROM emprestimo_parcela WHERE valor_parcela = 150.00 AND data_vencimento = '2024-08-01');

-- Inserir outra parcela com valor e data de vencimento específicos
INSERT INTO emprestimo_parcela (valor_parcela, data_vencimento)
SELECT 200.00, '2024-09-01'
WHERE NOT EXISTS (SELECT 1 FROM emprestimo_parcela WHERE valor_parcela = 200.00 AND data_vencimento = '2024-09-01');

-- Inserir mais uma parcela com valor e data de vencimento específicos
INSERT INTO emprestimo_parcela (valor_parcela, data_vencimento)
SELECT 250.00, '2024-10-01'
WHERE NOT EXISTS (SELECT 1 FROM emprestimo_parcela WHERE valor_parcela = 250.00 AND data_vencimento = '2024-10-01');

-- Criação da tabela EmprestimoRequisicao
CREATE TABLE IF NOT EXISTS emprestimo_requisicao (
    id_requisicao SERIAL PRIMARY KEY,
    id_cliente_fk INTEGER REFERENCES cliente(id_cliente),
    id_modalidade_fk INTEGER REFERENCES emprestimo_modalidade(id_modalidade),
    valor_requerido NUMERIC(15, 2) NOT NULL CHECK (valor_requerido > 0),
    dia_pagamento INTEGER NOT NULL, 
    data_requisicao DATE NOT NULL,
    aprovado BOOLEAN, 
    descricao_resultado VARCHAR(250), 
    data_resultado DATE, 
    prazo_mes INTEGER NOT NULL CHECK (prazo_mes > 0),
    valor_total NUMERIC(15, 2) CHECK (valor_total > 0),
    id_objetivo_fk INTEGER REFERENCES emprestimo_objetivo(id_objetivo),
    id_urgencia_fk INTEGER REFERENCES emprestimo_urgencia(id_urgencia),    
    id_iof_fk INTEGER REFERENCES iof_atual(id_iof),
    id_juros_fk INTEGER REFERENCES juros(id_juros),
    id_modalidade_pagamento_fk INTEGER REFERENCES modalidade_pagamento(id_modalidade_pagamento), 
    id_emprestimo_parcela_fk INTEGER REFERENCES emprestimo_parcela(id_emprestimo_parcela),
    juros_calculado NUMERIC(15, 2),
    iof_calculado NUMERIC(15, 2),
    valor_parcela NUMERIC(15, 2)
);

-- Inserir dados na tabela emprestimo_requisicao
INSERT INTO emprestimo_requisicao (id_cliente_fk, id_modalidade_fk, valor_requerido, 
    prazo_mes, valor_total, id_objetivo_fk, id_urgencia_fk, id_iof_fk, id_juros_fk, 
    id_modalidade_pagamento_fk, id_emprestimo_parcela_fk, data_requisicao, aprovado, descricao_resultado, data_resultado, dia_pagamento, juros_calculado, iof_calculado, valor_parcela)
SELECT 1, 1, 5000.00, 12, 5400.00, 1, 2, 1, 1, 1, 1, '2024-07-18', true, 'teste descricao', '2024-07-20', 12, 150.00, 50.00, 500.00
WHERE NOT EXISTS (SELECT 1 FROM emprestimo_requisicao WHERE data_requisicao = '2024-07-18' AND id_cliente_fk = 1);

INSERT INTO emprestimo_requisicao (id_cliente_fk, id_modalidade_fk, valor_requerido,
    prazo_mes, valor_total, id_objetivo_fk, id_urgencia_fk, id_iof_fk, id_juros_fk, 
    id_modalidade_pagamento_fk, id_emprestimo_parcela_fk, data_requisicao, aprovado, descricao_resultado, data_resultado, dia_pagamento, juros_calculado, iof_calculado, valor_parcela)
SELECT 2, 2, 2000.00, 15, 7980.00, 2, 1, 1, 1, 2, 2, '2024-07-20', true, 'teste descricao2', '2024-07-20', 15, 100.00, 20.00, 300.00
WHERE NOT EXISTS (SELECT 1 FROM emprestimo_requisicao WHERE data_requisicao = '2024-07-20' AND id_cliente_fk = 2);

INSERT INTO emprestimo_requisicao (id_cliente_fk, id_modalidade_fk, valor_requerido,
    prazo_mes, valor_total, id_objetivo_fk, id_urgencia_fk, id_iof_fk, id_juros_fk, 
    id_modalidade_pagamento_fk, id_emprestimo_parcela_fk, data_requisicao, aprovado, descricao_resultado, data_resultado, dia_pagamento, juros_calculado, iof_calculado, valor_parcela)
SELECT 3, 3, 10000.00, 20, 11800.00, 3, 3, 1, 3, 3, 3, '2024-07-21', true, 'teste descricao3', '2024-07-20', 30, 200.00, 100.00, 800.00
WHERE NOT EXISTS (SELECT 1 FROM emprestimo_requisicao WHERE data_requisicao = '2024-07-21' AND id_cliente_fk = 3);



-- Criação da tabela AnaliseRestricao
CREATE TABLE IF NOT EXISTS analise_restricao (
    id_restricao SERIAL PRIMARY KEY,
    id_cliente_fk INTEGER REFERENCES cliente(id_cliente),
    status_serasa BOOLEAN NOT NULL,
    status_spc BOOLEAN NOT NULL
);

-- Inserir dados na tabela analise_restricao apenas se não existirem registros
INSERT INTO analise_restricao (id_cliente_fk, status_serasa, status_spc)
SELECT 1, true, false
WHERE NOT EXISTS (SELECT 1 FROM analise_restricao);

INSERT INTO analise_restricao (id_cliente_fk, status_serasa, status_spc)
SELECT 2, false, true
WHERE NOT EXISTS (SELECT 1 FROM analise_restricao WHERE id_cliente_fk = 2);

INSERT INTO analise_restricao (id_cliente_fk, status_serasa, status_spc)
SELECT 3, true, true
WHERE NOT EXISTS (SELECT 1 FROM analise_restricao WHERE id_cliente_fk = 3);

-- Criação da tabela de PatrimonioTipo
CREATE TABLE IF NOT EXISTS patrimonio_tipo (
    id_patrimonio_tipo SERIAL PRIMARY KEY,
    descricao_patrimonio_tipo VARCHAR(100) NOT NULL 
);

-- Inserir dados na tabela patrimonio_tipo apenas se não existirem registros
INSERT INTO patrimonio_tipo (descricao_patrimonio_tipo)
SELECT 'Imóveis'
WHERE NOT EXISTS (SELECT 1 FROM patrimonio_tipo WHERE descricao_patrimonio_tipo = 'Imóveis');

INSERT INTO patrimonio_tipo (descricao_patrimonio_tipo)
SELECT 'Veículos'
WHERE NOT EXISTS (SELECT 1 FROM patrimonio_tipo WHERE descricao_patrimonio_tipo = 'Veículos');

INSERT INTO patrimonio_tipo (descricao_patrimonio_tipo)
SELECT 'Investimentos'
WHERE NOT EXISTS (SELECT 1 FROM patrimonio_tipo WHERE descricao_patrimonio_tipo = 'Investimentos');

-- Criação da tabela Patrimonio
CREATE TABLE IF NOT EXISTS patrimonio (
    id_patrimonio SERIAL PRIMARY KEY,
    id_cliente_fk INTEGER REFERENCES cliente(id_cliente),
    id_patrimonio_tipo_fk INTEGER REFERENCES patrimonio_tipo(id_patrimonio_tipo),
    valor_patrimonio NUMERIC(15, 2) NOT NULL CHECK (valor_patrimonio >= 0)
);

-- Inserir dados na tabela patrimonio apenas se não existirem registros
INSERT INTO patrimonio (id_cliente_fk, id_patrimonio_tipo_fk, valor_patrimonio)
SELECT 1, 1, 500000.00
WHERE NOT EXISTS (
    SELECT 1 
    FROM patrimonio 
    WHERE id_cliente_fk = 1 
    AND id_patrimonio_tipo_fk = 1
);

INSERT INTO patrimonio (id_cliente_fk, id_patrimonio_tipo_fk, valor_patrimonio)
SELECT 2, 2, 100000.00
WHERE NOT EXISTS (
    SELECT 1 
    FROM patrimonio 
    WHERE id_cliente_fk = 2 
    AND id_patrimonio_tipo_fk = 2
);

INSERT INTO patrimonio (id_cliente_fk, id_patrimonio_tipo_fk, valor_patrimonio)
SELECT 3, 3, 250000.00
WHERE NOT EXISTS (
    SELECT 1 
    FROM patrimonio 
    WHERE id_cliente_fk = 3 
    AND id_patrimonio_tipo_fk = 3
);

-- Criação da tabela EmprestimoResultado
CREATE TABLE IF NOT EXISTS emprestimo_resultado (
    id_resultado SERIAL PRIMARY KEY,
    id_requisicao_fk INTEGER REFERENCES emprestimo_requisicao(id_requisicao),
    aprovado BOOLEAN NOT NULL,
    descricao_resultado TEXT
);

-- Inserir dados na tabela emprestimo_resultado apenas se não existirem registros
INSERT INTO emprestimo_resultado (id_requisicao_fk, aprovado, descricao_resultado)
SELECT 1, true, 'Aprovado'
WHERE NOT EXISTS (
    SELECT 1 
    FROM emprestimo_resultado 
    WHERE id_requisicao_fk = 1
);

INSERT INTO emprestimo_resultado (id_requisicao_fk, aprovado, descricao_resultado)
SELECT 2, false, 'Reprovado'
WHERE NOT EXISTS (
    SELECT 1 
    FROM emprestimo_resultado 
    WHERE id_requisicao_fk = 2
);

INSERT INTO emprestimo_resultado (id_requisicao_fk, aprovado, descricao_resultado)
SELECT 3, true, 'Aprovado com restrições'
WHERE NOT EXISTS (
    SELECT 1 
    FROM emprestimo_resultado 
    WHERE id_requisicao_fk = 3
);
