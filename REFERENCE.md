# 🏦 Sistema Bancário Educacional - Quick Reference

## ⚡ Comandos Rápidos

### Compilar
```bash
mvn clean compile
```

### Executar
```bash
mvn spring-boot:run
```

### Limpar Build
```bash
mvn clean
```

### Gerar JAR
```bash
mvn package
```

---

## 🔗 URLs Importantes

| URL | Descrição |
|-----|-----------|
| http://localhost:8081 | Página de login |
| http://localhost:8081/home | Dashboard principal |
| http://localhost:8081/banco/frentecaixaeletronico | Menu do ATM |
| http://localhost:8081/h2-console | Console H2 |

---

## 🔐 Credenciais de Teste

| Campo | Valor |
|-------|-------|
| Usuário | drm |
| Senha | 12345 |

### Contas Pré-cadastradas

| Número | Agência | CPF | Saldo |
|--------|---------|-----|-------|
| 001234 | 0001 | 123.456.789-00 | R$ 1.000,00 |
| 001235 | 0001 | 987.654.321-00 | R$ 500,00 |

---

## 📁 Estrutura de Pastas

```
src/main/
├── java/
│   ├── faculdade/login/
│   │   ├── MinhasRotas.java (Controller)
│   │   ├── service/
│   │   │   └── OperacaoBancariaService.java
│   │   └── repository/
│   │       ├── ClienteDAO.java
│   │       ├── ContaCorrenteDAO.java
│   │       └── TransacaoDAO.java
│   └── model/entidades/
│       ├── Cliente.java
│       ├── ContaCorrente.java
│       ├── Transacao.java
│       ├── Pedido.java
│       ├── Produto.java
│       └── ItemPedido.java
└── resources/
    ├── templates/ (HTML)
    ├── static/css/ (CSS)
    ├── application.properties
    └── data.sql (DDL)
```

---

## 🔄 Fluxo de Uma Operação

```
HTML Form (user input)
    ↓
HTTP POST/GET
    ↓
Controller (MinhasRotas)
    ↓
Service (OperacaoBancariaService)
    ↓
DAO (ContaCorrenteDAO)
    ↓
Database (H2)
    ↓
Response HTML
```

---

## 🎯 Endpoints Principais

### Login
- `GET /login` - Página de login
- `POST /login` - Processar login

### Home
- `GET /home` - Dashboard principal

### Caixa Eletrônico
- `GET /banco/frentecaixaeletronico` - Menu ATM
- `GET/POST /banco/saque` - Saque
- `GET/POST /banco/deposito` - Depósito
- `GET/POST /banco/extrato` - Extrato
- `GET/POST /banco/saldo` - Saldo
- `GET/POST /banco/cadastrocontacorrente` - Cadastro

---

## 🛠️ Troubleshooting

| Problema | Solução |
|----------|---------|
| Port 8081 em uso | `mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8082"` |
| Maven não encontrado | Instale Maven: https://maven.apache.org/ |
| Java versão incorreta | Configure JAVA_HOME para Java 21+ |
| H2 não conecta | Verifique URL: `jdbc:h2:mem:test` |

---

## 📚 Documentação Completa

- [README.md](README.md) - Visão geral
- [GUIA_APRENDIZADO.md](GUIA_APRENDIZADO.md) - Tutorial completo
- [MELHORIAS.md](MELHORIAS.md) - O que foi melhorado
- [STATUS.md](STATUS.md) - Status do projeto

---

## 🎓 Conceitos

- **MVC**: Model-View-Controller
- **DAO**: Data Access Object
- **Service**: Lógica de negócio
- **Repository**: Padrão de acesso a dados
- **Spring Boot**: Framework Java
- **Thymeleaf**: Template engine
- **H2**: Banco de dados
- **JDBC**: Acesso a banco de dados

---

## ✨ Features

✅ Saque com validações  
✅ Depósito  
✅ Extrato/Histórico  
✅ Consulta de saldo  
✅ Cadastro de conta  
✅ Design moderno  
✅ Responsivo  
✅ Comentários educacionais  

---

**Última atualização**: Abril 2024
