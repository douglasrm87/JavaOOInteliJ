package faculdade.login.service;

import model.entidades.ContaCorrente;
import model.entidades.Transacao;
import faculdade.login.repository.ContaCorrenteDAO;
import faculdade.login.repository.TransacaoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Serviço de Operações Bancárias
 * 
 * Esta classe implementa a lógica de negócio para as operações bancárias.
 * Segue o padrão Service, que separa a lógica de negócio da camada de acesso a dados (DAO).
 * 
 * Padrão de Design: Service Pattern
 * Benefícios:
 * - Facilita testes unitários
 * - Reutiliza código de negócio
 * - Centraliza validações
 * - Melhora manutenibilidade
 */
@Service
public class OperacaoBancariaService {

    // Limite de saque diário
    private static final BigDecimal LIMITE_SAQUE_DIARIO = new BigDecimal("5000.00");

    @Autowired
    private ContaCorrenteDAO contaCorrenteDAO;

    @Autowired
    private TransacaoDAO transacaoDAO;

    /**
     * Realiza um saque na conta corrente
     * 
     * Validações:
     * - Conta deve existir
     * - Saldo deve ser suficiente
     * - Valor deve estar dentro do limite diário
     * - Valor deve ser positivo
     * 
     * @param numeroConta Número da conta
     * @param agencia Agência da conta
     * @param cpf CPF do titular
     * @param valor Valor a sacar
     * @return Mensagem de sucesso ou erro
     */
    public String realizarSaque(String numeroConta, String agencia, String cpf, BigDecimal valor) {
        // Validação 1: Valor deve ser positivo
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            return "Erro: O valor do saque deve ser maior que zero.";
        }

        // Validação 2: Valor não pode exceder limite diário
        if (valor.compareTo(LIMITE_SAQUE_DIARIO) > 0) {
            return "Erro: Saque excede o limite diário de R$ 5.000,00.";
        }

        // Buscar conta
        ContaCorrente conta = contaCorrenteDAO.obterPorNumeroConta(numeroConta);

        // Validação 3: Conta deve existir
        if (conta == null) {
            return "Erro: Conta não encontrada.";
        }

        // Validação 4: CPF deve ser correspondente
        if (!conta.getCpf().equals(cpf) || !conta.getAgencia().equals(agencia)) {
            return "Erro: Dados da conta não conferem.";
        }

        // Validação 5: Conta deve estar ativa
        if (!conta.getAtiva()) {
            return "Erro: Conta inativa.";
        }

        // Validação 6: Saldo deve ser suficiente
        if (conta.getSaldo().compareTo(valor) < 0) {
            return "Erro: Saldo insuficiente para realizar o saque.";
        }

        // Executar saque
        BigDecimal novoSaldo = conta.getSaldo().subtract(valor);
        contaCorrenteDAO.atualizarSaldo(conta.getId(), novoSaldo);

        // Registrar transação
        Transacao transacao = new Transacao(conta.getId(), "SAQUE", valor, "Saque no ATM");
        transacaoDAO.salvarTransacao(transacao);

        return "Saque realizado com sucesso! Novo saldo: R$ " + novoSaldo;
    }

    /**
     * Realiza um depósito na conta corrente
     * 
     * @param numeroConta Número da conta
     * @param agencia Agência da conta
     * @param cpf CPF do titular
     * @param valor Valor a depositar
     * @param descricao Descrição do depósito (opcional)
     * @return Mensagem de sucesso ou erro
     */
    public String realizarDeposito(String numeroConta, String agencia, String cpf, BigDecimal valor, String descricao) {
        // Validação 1: Valor deve ser positivo
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            return "Erro: O valor do depósito deve ser maior que zero.";
        }

        // Buscar conta
        ContaCorrente conta = contaCorrenteDAO.obterPorNumeroConta(numeroConta);

        // Validação 2: Conta deve existir
        if (conta == null) {
            return "Erro: Conta não encontrada.";
        }

        // Validação 3: CPF deve ser correspondente
        if (!conta.getCpf().equals(cpf) || !conta.getAgencia().equals(agencia)) {
            return "Erro: Dados da conta não conferem.";
        }

        // Validação 4: Conta deve estar ativa
        if (!conta.getAtiva()) {
            return "Erro: Conta inativa.";
        }

        // Executar depósito
        BigDecimal novoSaldo = conta.getSaldo().add(valor);
        contaCorrenteDAO.atualizarSaldo(conta.getId(), novoSaldo);

        // Registrar transação
        String descricaoTransacao = descricao != null && !descricao.isEmpty() ? descricao : "Depósito no ATM";
        Transacao transacao = new Transacao(conta.getId(), "DEPOSITO", valor, descricaoTransacao);
        transacaoDAO.salvarTransacao(transacao);

        return "Depósito realizado com sucesso! Novo saldo: R$ " + novoSaldo;
    }

    /**
     * Consulta o saldo de uma conta
     * 
     * @param numeroConta Número da conta
     * @param agencia Agência da conta
     * @param cpf CPF do titular
     * @return Objeto ContaCorrente ou null se não encontrada
     */
    public ContaCorrente consultarSaldo(String numeroConta, String agencia, String cpf) {
        ContaCorrente conta = contaCorrenteDAO.obterPorNumeroConta(numeroConta);

        if (conta == null) {
            return null;
        }

        // Validar dados
        if (!conta.getCpf().equals(cpf) || !conta.getAgencia().equals(agencia)) {
            return null;
        }

        return conta;
    }

    /**
     * Obtém o histórico de transações de uma conta
     * 
     * @param idConta ID da conta
     * @return Lista de transações
     */
    public List<Transacao> obterHistoricoTransacoes(Integer idConta) {
        return transacaoDAO.obterPorContaCorrente(idConta);
    }

    /**
     * Cadastra uma nova conta corrente
     * 
     * @param numeroConta Número da conta
     * @param agencia Agência
     * @param cpf CPF do titular
     * @param saldoInicial Saldo inicial (opcional)
     * @return Mensagem de sucesso ou erro
     */
    public String cadastrarContaCorrente(String numeroConta, String agencia, String cpf, BigDecimal saldoInicial) {
        // Validação 1: CPF não pode ser duplicado
        if (contaCorrenteDAO.obterPorCpf(cpf) != null) {
            return "Erro: Já existe uma conta cadastrada com este CPF.";
        }

        // Validação 2: Número da conta não pode ser duplicado
        if (contaCorrenteDAO.obterPorNumeroConta(numeroConta) != null) {
            return "Erro: Já existe uma conta com este número.";
        }

        // Criar nova conta
        ContaCorrente novaConta = new ContaCorrente(numeroConta, agencia, cpf);
        if (saldoInicial != null && saldoInicial.compareTo(BigDecimal.ZERO) > 0) {
            novaConta.setSaldo(saldoInicial);
        }

        // Salvar
        contaCorrenteDAO.salvarConta(novaConta);

        return "Conta cadastrada com sucesso!";
    }
}
