-- =========================================
-- Tabelas do módulo de Vendas
-- =========================================

create table cliente (
    id integer primary key auto_increment,
    nome varchar (100)
);

create table produto (
    id integer primary key auto_increment,
    descricao varchar (100),
    preco_unitario numeric (20,2)
);

create table pedido (
    id integer primary key auto_increment,
    cliente_id integer references cliente (id),
    data_pedido timestamp,
    total numeric (20,2)
);

create table item_pedido (
    id integer primary key auto_increment,
    pedido_id integer references pedido (id),
    produto_id integer references produto (id),
    quantidade integer
);

-- =========================================
-- Tabelas do módulo de Banco/Frente de Caixa
-- =========================================

-- Tabela de Contas Correntes
-- Armazena informações de contas bancárias
create table conta_corrente (
    id integer primary key auto_increment,
    numero_conta varchar(20) unique not null,
    agencia varchar(10) not null,
    cpf varchar(14) not null unique,
    saldo numeric(20,2) default 0.00,
    ativa boolean default true,
    data_criacao timestamp default current_timestamp
);

-- Tabela de Transações
-- Registra todas as movimentações bancárias (saque, depósito, transferência)
-- Serves as audit trail (registro de auditoria)
create table transacao (
    id integer primary key auto_increment,
    id_conta_corrente integer not null references conta_corrente(id),
    tipo varchar(20) not null,
    valor numeric(20,2) not null,
    data_hora timestamp default current_timestamp,
    descricao varchar(200)
);

-- =========================================
-- Dados iniciais para testes (opcional)
-- =========================================

-- Inserir alguns clientes iniciais
INSERT INTO cliente (nome) VALUES ('Cliente Padrão');

-- Inserir uma conta corrente para teste
INSERT INTO conta_corrente (numero_conta, agencia, cpf, saldo, ativa)
VALUES ('001234', '0001', '123.456.789-00', 1000.00, true);

INSERT INTO conta_corrente (numero_conta, agencia, cpf, saldo, ativa)
VALUES ('001235', '0001', '987.654.321-00', 500.00, true);