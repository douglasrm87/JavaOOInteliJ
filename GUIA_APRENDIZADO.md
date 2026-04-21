# 📚 Guia de Aprendizado - Sistema Bancário Educacional

Este documento oferece um roteiro de aprendizado estruturado para entender a arquitetura e o funcionamento do Sistema Bancário Educacional.

## 🎯 Objetivos de Aprendizado

Ao estudar este projeto, você aprenderá sobre:

1. **Arquitetura em Camadas (Layered Architecture)**
2. **Padrão MVC (Model-View-Controller)**
3. **Padrão DAO (Data Access Object)**
4. **Service Layer (Camada de Serviços)**
5. **Dependency Injection (Injeção de Dependências)**
6. **Validação de Regras de Negócio**
7. **Persistência de Dados com JDBC**
8. **Desenvolvimento Web com Spring Boot**
9. **Template Engines (Thymeleaf)**
10. **Boas Práticas em Desenvolvimento Java**

---

## 📊 Fluxo da Aplicação

```
┌─────────────────────────────────────────────────────────────┐
│                    NAVEGADOR DO USUÁRIO                      │
│                  (login.html, home.html, etc)                │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ↓ HTTP Request
┌─────────────────────────────────────────────────────────────┐
│                      CONTROLLER                              │
│              (MinhasRotas.java)                              │
│  - Recebe requisições HTTP                                  │
│  - Mapeia URLs para métodos                                 │
│  - Valida dados iniciais                                    │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ↓ Chama métodos de negócio
┌─────────────────────────────────────────────────────────────┐
│                    SERVICE LAYER                             │
│        (OperacaoBancariaService.java)                        │
│  - Validações de negócio                                    │
│  - Regras de operações (limites, etc)                       │
│  - Orquestra operações complexas                            │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ↓ Acessa dados através de DAOs
┌─────────────────────────────────────────────────────────────┐
│                    DAO LAYER                                 │
│  - ContaCorrenteDAO.java                                    │
│  - TransacaoDAO.java                                        │
│  - ClienteDAO.java                                          │
│  - SQL queries                                              │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ↓ Executa queries
┌─────────────────────────────────────────────────────────────┐
│                    DATABASE (H2)                             │
│  - conta_corrente                                           │
│  - transacao                                                │
│  - cliente                                                  │
└─────────────────────────────────────────────────────────────┘
```

---

## 🏗️ Estrutura de Pacotes

### 📦 `model.entidades`
**Responsabilidade**: Definir as entidades do domínio

```
Cliente.java          → Representa um cliente
ContaCorrente.java    → Representa uma conta corrente
Transacao.java        → Representa uma transação bancária
Pedido.java           → Representa um pedido de venda
Produto.java          → Representa um produto
ItemPedido.java       → Representa um item de pedido
```

**Características**:
- Classes simples (POJOs - Plain Old Java Objects)
- Getters e Setters
- Mapeiam diretamente a tabelas do banco
- Sem lógica de negócio complexa

---

### 📦 `faculdade.login.repository`
**Responsabilidade**: Acesso a dados (Data Access Object)

```
ClienteDAO.java          → CRUD de clientes
ContaCorrenteDAO.java    → CRUD de contas correntes
TransacaoDAO.java        → Registro e consulta de transações
```

**Padrão DAO**:
```
┌─────────────────────────────────────────┐
│         OBJETO DO DOMÍNIO               │
│      (Cliente, ContaCorrente)           │
└─────────────────────────────────────────┘
              ↕ Mapear
┌─────────────────────────────────────────┐
│       REGISTROS DA TABELA (ResultSet)   │
│         do banco de dados               │
└─────────────────────────────────────────┘
```

**Exemplo de Método DAO**:
```java
public ContaCorrente obterPorCpf(String cpf) {
    List<ContaCorrente> contas = jdbcTemplate.query(
        SELECT_BY_CPF, 
        new Object[]{cpf}, 
        new ContaCorrenteRowMapper()
    );
    return contas.isEmpty() ? null : contas.get(0);
}
```

---

### 📦 `faculdade.login.service`
**Responsabilidade**: Lógica de negócio

```
OperacaoBancariaService.java → Orquestra operações bancárias
```

**Validações Implementadas**:

1. **Saque**:
   - ✓ Valor deve ser positivo
   - ✓ Não pode exceder limite diário (R$ 5.000)
   - ✓ Conta deve existir
   - ✓ CPF deve corresponder
   - ✓ Conta deve estar ativa
   - ✓ Saldo deve ser suficiente

2. **Depósito**:
   - ✓ Valor deve ser positivo
   - ✓ Conta deve existir
   - ✓ CPF deve corresponder
   - ✓ Conta deve estar ativa

**Exemplo de Validação**:
```java
public String realizarSaque(String numeroConta, String agencia, 
                           String cpf, BigDecimal valor) {
    // Validação 1: Valor positivo
    if (valor.compareTo(BigDecimal.ZERO) <= 0) {
        return "Erro: Valor deve ser maior que zero.";
    }
    
    // Validação 2: Limite diário
    if (valor.compareTo(LIMITE_SAQUE_DIARIO) > 0) {
        return "Erro: Saque excede limite diário.";
    }
    
    // ... outras validações
    
    // Executar operação
    BigDecimal novoSaldo = conta.getSaldo().subtract(valor);
    contaCorrenteDAO.atualizarSaldo(conta.getId(), novoSaldo);
    
    // Registrar transação para auditoria
    Transacao transacao = new Transacao(...);
    transacaoDAO.salvarTransacao(transacao);
    
    return "Saque realizado com sucesso!";
}
```

---

### 📦 `faculdade.login` (Controller)
**Responsabilidade**: Controlar fluxo HTTP

```
MinhasRotas.java → Define todas as rotas da aplicação
```

**Padrão de Rota**:
```java
@RequestMapping(value = "/banco/saque", method = RequestMethod.POST)
public String processarSaque(
        @RequestParam String numeroConta,
        @RequestParam String agencia,
        @RequestParam String cpf,
        @RequestParam BigDecimal valor,
        ModelMap model) {
    
    // 1. Chamar serviço
    String resultado = operacaoBancariaService.realizarSaque(...);
    
    // 2. Adicionar resultado ao model (dados para a view)
    model.addAttribute("mensagem", resultado);
    
    // 3. Retornar nome da view (template HTML)
    return "/banco/saque";
}
```

---

## 🔄 Ciclo de Vida de Uma Requisição

### Exemplo: Realizar Saque

```
1. USUÁRIO ACESSA PÁGINA
   GET /banco/saque
        ↓
2. CONTROLLER RECEBE REQUISIÇÃO
   MinhasRotas.carregarSaque()
        ↓
3. RETORNA FORMULÁRIO
   /templates/banco/saque.html
        ↓
4. USUÁRIO PREENCHE FORMULÁRIO E SUBMETE
   POST /banco/saque
        ↓
5. CONTROLLER RECEBE DADOS
   @RequestParam extraí automaticamente os valores
        ↓
6. CONTROLLER CHAMA SERVIÇO
   operacaoBancariaService.realizarSaque(...)
        ↓
7. SERVIÇO VALIDA DADOS
   - Verifica saldo
   - Verifica limite
   - Verifica se conta existe
        ↓
8. SERVIÇO ACESSA DAO PARA ATUALIZAR DADOS
   contaCorrenteDAO.atualizarSaldo(...)
   transacaoDAO.salvarTransacao(...)
        ↓
9. DAO EXECUTA QUERIES SQL
   UPDATE conta_corrente SET saldo = ? WHERE id = ?
   INSERT INTO transacao (...)
        ↓
10. BANCO EXECUTA E RETORNA RESULTADO
        ↓
11. SERVIÇO RETORNA MENSAGEM AO CONTROLLER
        ↓
12. CONTROLLER ADICIONA MENSAGEM AO MODEL
        ↓
13. THYMELEAF RENDERIZA HTML COM DADOS
        ↓
14. NAVEGADOR EXIBE RESPOSTA AO USUÁRIO
```

---

## 💾 Banco de Dados

### Tabelas

#### `conta_corrente`
```sql
CREATE TABLE conta_corrente (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    numero_conta VARCHAR(20) UNIQUE NOT NULL,
    agencia VARCHAR(10) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    saldo NUMERIC(20,2) DEFAULT 0.00,
    ativa BOOLEAN DEFAULT true,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Campos**:
- `id`: Identificador único (gerado automaticamente)
- `numero_conta`: Número da conta (único)
- `agencia`: Número da agência
- `cpf`: CPF do titular (único)
- `saldo`: Saldo atual
- `ativa`: Flag de atividade
- `data_criacao`: Quando a conta foi criada

---

#### `transacao`
```sql
CREATE TABLE transacao (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_conta_corrente INTEGER NOT NULL REFERENCES conta_corrente(id),
    tipo VARCHAR(20) NOT NULL,
    valor NUMERIC(20,2) NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    descricao VARCHAR(200)
);
```

**Campos**:
- `id`: Identificador único
- `id_conta_corrente`: Referência à conta
- `tipo`: SAQUE, DEPOSITO, TRANSFERENCIA
- `valor`: Valor da transação
- `data_hora`: Quando ocorreu
- `descricao`: Descrição adicional

**Importância**: Registro de auditoria de todas as operações

---

## 🎨 Frontend (Templates)

### Arquitetura de CSS

```
base.css        → Estilos base (cores, tipografia, componentes comuns)
banco.css       → Estilos específicos do módulo bancário (ATM)
```

### Componentes Reutilizáveis

**Card de Menu**:
```html
<div class="menu-card">
    <div class="menu-card-icon">🏧</div>
    <h3>Título</h3>
    <p>Descrição</p>
    <div class="menu-card-links">
        <a href="..." class="menu-card-link">Acessar</a>
    </div>
</div>
```

**Formulário**:
```html
<form th:action="@{/banco/saque}" method="post">
    <div class="form-section">
        <h2>Seção do Formulário</h2>
        <div class="form-group">
            <label for="campo">Rótulo:</label>
            <input type="text" id="campo" name="campo" required />
        </div>
    </div>
    <button type="submit" class="atm-button confirm">Confirmar</button>
</form>
```

---

## 🔍 Exemplo Detalhado: Realizar um Saque

### Passo 1: Usuário Clica em "Saque"
```html
<!-- Na página /banco/frentecaixaeletronico -->
<form th:action="@{/banco/saque}" method="get">
    <button type="submit" class="atm-button">
        <span>💸</span> Saque
    </button>
</form>
```

### Passo 2: Controller Carrega Formulário
```java
@RequestMapping(value = "/banco/saque", method = RequestMethod.GET)
public String carregarSaque(ModelMap model) {
    return "/banco/saque";  // Retorna formulário vazio
}
```

### Passo 3: Usuário Preenche e Submete
```html
<!-- saque.html -->
<form th:action="@{/banco/saque}" method="post">
    <input type="text" name="numeroConta" required />
    <input type="text" name="agencia" required />
    <input type="text" name="cpf" required />
    <input type="number" name="valor" step="0.01" required />
    <button type="submit">Confirmar Saque</button>
</form>
```

### Passo 4: Controller Processa Saque
```java
@RequestMapping(value = "/banco/saque", method = RequestMethod.POST)
public String processarSaque(
        @RequestParam String numeroConta,
        @RequestParam String agencia,
        @RequestParam String cpf,
        @RequestParam BigDecimal valor,
        ModelMap model) {
    
    // Chamar serviço
    String resultado = operacaoBancariaService.realizarSaque(
        numeroConta, agencia, cpf, valor
    );
    
    // Adicionar resultado ao model
    if (resultado.contains("Erro")) {
        model.addAttribute("erro", resultado);
    } else {
        model.addAttribute("mensagem", resultado);
    }
    
    // Voltar para mesma página com mensagem
    return "/banco/saque";
}
```

### Passo 5: Serviço Executa Operação
```java
public String realizarSaque(String numeroConta, String agencia, 
                           String cpf, BigDecimal valor) {
    
    // Validações
    if (valor.compareTo(BigDecimal.ZERO) <= 0) {
        return "Erro: Valor deve ser maior que zero.";
    }
    
    if (valor.compareTo(LIMITE_SAQUE_DIARIO) > 0) {
        return "Erro: Saque excede limite diário de R$ 5.000,00.";
    }
    
    // Buscar conta
    ContaCorrente conta = contaCorrenteDAO.obterPorNumeroConta(numeroConta);
    
    if (conta == null) {
        return "Erro: Conta não encontrada.";
    }
    
    // Validar CPF e agência
    if (!conta.getCpf().equals(cpf) || !conta.getAgencia().equals(agencia)) {
        return "Erro: Dados da conta não conferem.";
    }
    
    // Validar saldo
    if (conta.getSaldo().compareTo(valor) < 0) {
        return "Erro: Saldo insuficiente.";
    }
    
    // Executar saque
    BigDecimal novoSaldo = conta.getSaldo().subtract(valor);
    contaCorrenteDAO.atualizarSaldo(conta.getId(), novoSaldo);
    
    // Registrar transação
    Transacao transacao = new Transacao(
        conta.getId(), 
        "SAQUE", 
        valor, 
        "Saque no ATM"
    );
    transacaoDAO.salvarTransacao(transacao);
    
    return "Saque realizado com sucesso! Novo saldo: R$ " + novoSaldo;
}
```

### Passo 6: DAO Executa SQL
```java
public void atualizarSaldo(Integer idConta, BigDecimal novoSaldo) {
    // SQL: UPDATE conta_corrente SET saldo = ? WHERE id = ?
    jdbcTemplate.update(UPDATE_SALDO, novoSaldo, idConta);
}

public Transacao salvarTransacao(Transacao transacao) {
    // SQL: INSERT INTO transacao (id_conta_corrente, tipo, valor, ...)
    jdbcTemplate.update(INSERT,
        transacao.getIdContaCorrente(),
        transacao.getTipo(),
        transacao.getValor(),
        Timestamp.valueOf(transacao.getDataHora()),
        transacao.getDescricao()
    );
    return transacao;
}
```

### Passo 7: Thymeleaf Renderiza Resposta
```html
<!-- saque.html - Thymeleaf -->
<div th:if="${mensagem}" class="success-message" th:text="${mensagem}"></div>
<!-- Exibe: "Saque realizado com sucesso! Novo saldo: R$ 900,00" -->
```

---

## 📚 Conceitos-Chave

### 1. Dependency Injection
```java
// Spring injeta automaticamente a dependência
@Autowired
private OperacaoBancariaService operacaoBancariaService;

// Sem injeção (acoplamento):
OperacaoBancariaService service = new OperacaoBancariaService(); // ❌
```

### 2. Separação de Responsabilidades
```
Controller    → HTTP e roteamento
Service       → Validações e regras de negócio
DAO           → Acesso a dados
Entity        → Representação de dados
```

### 3. RowMapper (Mapeamento de Dados)
```java
// Converte resultado SQL para objeto Java
private static class ContaCorrenteRowMapper implements RowMapper<ContaCorrente> {
    public ContaCorrente mapRow(ResultSet rs, int rowNum) throws SQLException {
        ContaCorrente conta = new ContaCorrente();
        conta.setId(rs.getInt("id"));
        conta.setNumeroConta(rs.getString("numero_conta"));
        // ... outros campos
        return conta;
    }
}
```

### 4. Validações em Cascata
```
Validação no Frontend (HTML5)
    ↓
Validação no Controller (@RequestParam)
    ↓
Validação no Service (Regras de Negócio)
    ↓
Validação no DAO (Constraints do Banco)
```

---

## 🧪 Exercícios Propostos

### 1. Adicionar Transferência Bancária
- Criar entrada `tipo` TRANSFERENCIA
- Validar contas origem e destino
- Decrementar saldo da origem
- Incrementar saldo do destino
- Registrar duas transações

### 2. Implementar Limite de Crédito
- Adicionar campo `limiteCred` na `ContaCorrente`
- Permitir saque até `saldo + limiteCreditito`
- Cobrar juros em operação de crédito

### 3. Adicionar Autenticação Segura
- Implementar login com usuário/senha
- Usar bcrypt para criptografar senhas
- Validar sessão do usuário

### 4. Adicionar Relatórios
- Saldo por agência
- Transações por período
- Clientes com menor saldo
- Exportar para PDF

### 5. Melhorar Performance
- Adicionar cache de consultas
- Implementar paginação
- Criar índices no banco

---

## 📖 Referências

- **Spring Boot**: https://spring.io/projects/spring-boot
- **Thymeleaf**: https://www.thymeleaf.org/
- **H2 Database**: https://www.h2database.com/
- **Padrão MVC**: https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller
- **Padrão DAO**: https://en.wikipedia.org/wiki/Data_access_object

---

## ✅ Checklist de Aprendizado

- [ ] Entendo o padrão MVC
- [ ] Entendo o padrão DAO
- [ ] Sei como funciona Dependency Injection
- [ ] Consigo criar uma nova entidade
- [ ] Consigo criar um novo DAO
- [ ] Consigo adicionar um novo serviço
- [ ] Consigo criar uma nova rota no controller
- [ ] Entendo como Thymeleaf funciona
- [ ] Consigo escrever queries SQL corretas
- [ ] Consigo debugar uma aplicação Spring Boot

---

**Bom aprendizado! 🚀**
