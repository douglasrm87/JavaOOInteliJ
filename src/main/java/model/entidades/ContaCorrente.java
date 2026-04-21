package model.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma Conta Corrente no sistema bancário.
 * Esta classe é utilizada para gerenciar dados de contas bancárias.
 * 
 * Atributos:
 * - id: Identificador único da conta (auto-incremento)
 * - numeroConta: Número único da conta corrente
 * - agencia: Número da agência bancária
 * - cpf: CPF do titular da conta
 * - saldo: Saldo atual da conta
 * - ativa: Flag indicando se a conta está ativa
 * - dataCriacao: Data de criação da conta
 */
public class ContaCorrente {
    
    private Integer id;
    private String numeroConta;
    private String agencia;
    private String cpf;
    private BigDecimal saldo;
    private Boolean ativa;
    private LocalDateTime dataCriacao;

    // Construtores
    public ContaCorrente() {
        this.saldo = BigDecimal.ZERO;
        this.ativa = true;
        this.dataCriacao = LocalDateTime.now();
    }

    public ContaCorrente(String numeroConta, String agencia, String cpf) {
        this();
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.cpf = cpf;
    }

    public ContaCorrente(Integer id, String numeroConta, String agencia, String cpf, BigDecimal saldo, Boolean ativa) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.cpf = cpf;
        this.saldo = saldo;
        this.ativa = ativa;
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "ContaCorrente{" +
                "id=" + id +
                ", numeroConta='" + numeroConta + '\'' +
                ", agencia='" + agencia + '\'' +
                ", cpf='" + cpf + '\'' +
                ", saldo=" + saldo +
                ", ativa=" + ativa +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
