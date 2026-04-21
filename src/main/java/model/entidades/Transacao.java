package model.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma Transação no sistema bancário.
 * Registra todas as movimentações de saque, depósito e transferências.
 * 
 * Atributos:
 * - id: Identificador único da transação (auto-incremento)
 * - idContaCorrente: ID da conta corrente envolvida
 * - tipo: Tipo de transação (SAQUE, DEPOSITO, TRANSFERENCIA)
 * - valor: Valor da transação
 * - dataHora: Data e hora da transação
 * - descricao: Descrição adicional da transação
 */
public class Transacao {
    
    public enum TipoTransacao {
        SAQUE("Saque"),
        DEPOSITO("Depósito"),
        TRANSFERENCIA("Transferência");

        private final String descricao;

        TipoTransacao(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    private Integer id;
    private Integer idContaCorrente;
    private String tipo; // SAQUE, DEPOSITO, TRANSFERENCIA
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String descricao;

    // Construtores
    public Transacao() {
        this.dataHora = LocalDateTime.now();
    }

    public Transacao(Integer idContaCorrente, String tipo, BigDecimal valor, String descricao) {
        this();
        this.idContaCorrente = idContaCorrente;
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdContaCorrente() {
        return idContaCorrente;
    }

    public void setIdContaCorrente(Integer idContaCorrente) {
        this.idContaCorrente = idContaCorrente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", idContaCorrente=" + idContaCorrente +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", dataHora=" + dataHora +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
