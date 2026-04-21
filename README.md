# 🏦 Sistema Bancário Educacional - Spring Boot

Um projeto educacional completo implementado em **Java Spring Boot** com banco de dados **H2**, demonstrando padrões de design e boas práticas de desenvolvimento.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Funcionalidades](#funcionalidades)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Como Executar](#como-executar)
- [Credenciais de Teste](#credenciais-de-teste)
- [Padrões de Design](#padrões-de-design)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Endpoints da API](#endpoints-da-api)

## 🎯 Visão Geral

Este projeto demonstra como construir uma aplicação web bancária educacional utilizando:

- **Java 21** com Spring Boot
- **Maven** para gerenciamento de dependências
- **H2 Database** para persistência de dados
- **Thymeleaf** para renderização de templates HTML
- **CSS moderno** com design responsivo

Ideal para alunos de tecnologia aprenderem sobre:
- Padrão MVC (Model-View-Controller)
- DAO (Data Access Object)
- Service Layer
- Validações de negócio
- Persistência de dados com JDBC
- Desenvolvimento web com Spring Boot

## ✨ Funcionalidades

### 🏧 Caixa Eletrônico
- **Saque**: Retirar valores da conta (limite: R$ 5.000,00/dia)
- **Depósito**: Adicionar valores na conta
- **Extrato**: Consultar histórico de transações
- **Saldo**: Consultar saldo atual
- **Cadastro de Conta**: Abrir nova conta corrente

### 💼 Sistema de Vendas (Educacional)
- Cadastro de clientes
- Consulta de clientes
- Gestão de pedidos
- Gestão de produtos

## 📁 Estrutura do Projeto

```
src/main/java/
├── faculdade/login/
│   ├── MinhasRotas.java              # Controller - Rotas da aplicação
│   ├── MinhaClasseFilterChain.java   # Filtro de requisições
│   ├── ProjetoWEB.java               # Classe principal do Spring Boot
│   ├── service/
│   │   └── OperacaoBancariaService.java  # Lógica de negócio
│   ├── repository/
│   │   ├── ClienteDAO.java           # Acesso a dados - Clientes
│   │   ├── ContaCorrenteDAO.java     # Acesso a dados - Contas
│   │   └── TransacaoDAO.java         # Acesso a dados - Transações
│   ├── rest/
│   │   └── ClienteController.java    # Controller REST (opcional)
│
└── model/entidades/
    ├── Cliente.java              # Entidade: Cliente
    ├── ContaCorrente.java        # Entidade: Conta Corrente
    ├── Transacao.java            # Entidade: Transação
    ├── Pedido.java               # Entidade: Pedido
    ├── Produto.java              # Entidade: Produto
    └── ItemPedido.java           # Entidade: Item do Pedido

src/main/resources/
├── templates/
│   ├── login.html                       # Tela de login
│   ├── home.html                        # Página inicial
│   ├── banco/
│   │   ├── frentecaixaeletronico.html   # Menu ATM
│   │   ├── cadastrocontacorrente.html   # Cadastro de conta
│   │   ├── saque.html                   # Tela de saque
│   │   ├── deposito.html                # Tela de depósito
│   │   ├── extrato.html                 # Tela de extrato
│   │   └── saldo.html                   # Tela de saldo
│   ├── cadastro.html                    # Cadastro de cliente
│   ├── consulta.html                    # Consulta de cliente
│   └── pedido.html                      # Gestão de pedidos
│
├── static/css/
│   ├── base.css                         # Estilos base (moderno)
│   ├── banco.css                        # Estilos específicos do banco
│   ├── casa.css                         # Estilos gerais
│   └── saudacao.css                     # Estilos de saudação
│
├── application.properties                # Configuração da aplicação
└── data.sql                             # Script de inicialização do BD

pom.xml                                  # Configuração Maven
```

## 🚀 Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.8+
- Git

### Passos

1. **Clone o repositório:**
```bash
git clone https://github.com/douglasrm87/JavaOOInteliJ.git
cd JavaOOInteliJ
```

2. **Compile e execute o projeto:**
```bash
mvn spring-boot:run
```

3. **Acesse a aplicação:**
```
http://localhost:8081
```

4. **Acesse o console H2:**
```
http://localhost:8081/h2-console
```
- URL JDBC: `jdbc:h2:mem:test`
- Usuário: `sa`
- Senha: `password`

## 🔐 Credenciais de Teste

### Login
- **Usuário**: `drm`
- **Senha**: `12345`

### Contas Pré-cadastradas
| Conta | Agência | CPF | Saldo |
|-------|---------|-----|-------|
| 001234 | 0001 | 123.456.789-00 | R$ 1.000,00 |
| 001235 | 0001 | 987.654.321-00 | R$ 500,00 |

## 🏗️ Padrões de Design

### 1. **MVC (Model-View-Controller)**
```
Controller (MinhasRotas)
    ↓
Service (OperacaoBancariaService)
    ↓
DAO (ContaCorrenteDAO)
    ↓
Database (H2)
```

### 2. **DAO (Data Access Object)**
Separa a lógica de acesso a dados da lógica de negócio:
```java
// Exemplo de uso
ContaCorrente conta = contaCorrenteDAO.obterPorCpf("123.456.789-00");
```

### 3. **Service Layer**
Centraliza a lógica de negócio e validações:
```java
// Exemplo de uso
String resultado = operacaoBancariaService.realizarSaque(conta, valor);
```

### 4. **Dependency Injection (IoC)**
O Spring gerencia automaticamente as dependências:
```java
@Autowired
private OperacaoBancariaService operacaoBancariaService;
```

## 💻 Tecnologias Utilizadas

| Tecnologia | Versão | Propósito |
|-----------|--------|----------|
| Java | 21 | Linguagem de programação |
| Spring Boot | 3.x | Framework web |
| Spring JDBC | 3.x | Acesso a banco de dados |
| H2 Database | 2.x | Banco de dados em memória |
| Thymeleaf | 3.x | Template engine |
| Maven | 3.8+ | Build tool |

## 🔗 Endpoints da API

### 🏠 Páginas Gerais
- `GET /` → Página de login
- `GET /login` → Tela de login
- `POST /login` → Processar login
- `GET /logout` → Sair do sistema
- `GET /home` → Página inicial

### 🏦 Caixa Eletrônico
- `GET /banco/frentecaixaeletronico` → Menu do ATM
- `GET /banco/saque` → Formulário de saque
- `POST /banco/saque` → Processar saque
- `GET /banco/deposito` → Formulário de depósito
- `POST /banco/deposito` → Processar depósito
- `GET /banco/extrato` → Formulário de extrato
- `POST /banco/extrato` → Processar extrato
- `GET /banco/saldo` → Formulário de saldo
- `POST /banco/saldo` → Processar saldo
- `GET /banco/cadastrocontacorrente` → Formulário de cadastro
- `POST /banco/cadastrocontacorrente` → Processar cadastro

### 👥 Sistema de Vendas
- `GET /cadastro` → Cadastro de cliente
- `POST /cadastro` → Processar cadastro
- `GET /consulta` → Consulta de cliente
- `POST /consulta` → Processar consulta
- `GET /pedido` → Gestão de pedidos
- `GET /hello` → Página informativa

## 📚 Exemplos de Uso

### Exemplo 1: Realizando um Saque

```java
// No controller
String resultado = operacaoBancariaService.realizarSaque(
    "001234",           // Número da conta
    "0001",             // Agência
    "123.456.789-00",   // CPF
    new BigDecimal("100.00")  // Valor
);
```

### Exemplo 2: Consultando Saldo

```java
ContaCorrente conta = operacaoBancariaService.consultarSaldo(
    "001234",
    "0001",
    "123.456.789-00"
);

System.out.println("Saldo: " + conta.getSaldo());
```

### Exemplo 3: Obtendo Histórico de Transações

```java
List<Transacao> transacoes = operacaoBancariaService
    .obterHistoricoTransacoes(idConta);

for (Transacao t : transacoes) {
    System.out.println(t.getTipo() + ": R$ " + t.getValor());
}
```

## 🎓 Aprendizado

Este projeto foi desenvolvido para ensinar:

1. **Arquitetura de Camadas**: Controller → Service → DAO
2. **Padrões de Design**: MVC, DAO, Service, Dependency Injection
3. **Persistência de Dados**: JDBC, JdbcTemplate do Spring
4. **Validação de Negócio**: Regras e limitações
5. **Desenvolvimento Web**: MVC com Thymeleaf
6. **Design Responsivo**: CSS moderno
7. **Boas Práticas**: Código limpo, comentários, organização

## 🤝 Contribuindo

Este é um projeto educacional. Sinta-se livre para:
- Melhorar a documentação
- Adicionar novos recursos
- Refatorar o código
- Compartilhar feedbacks

## 📝 Licença

Este projeto é educacional e está disponível para fins de aprendizado.

## 📧 Contato

Desenvolvido como projeto educacional em Java Spring Boot.

---

**Última atualização**: Abril 2024  
**Status**: Projeto Educacional ✓
