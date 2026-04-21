package faculdade.revisao.impl;

import faculdade.revisao.exception.PagamentoException;
import faculdade.revisao.interfaces.MetodoPagamento;

/**
 * 💳 IMPLEMENTAÇÃO - CartaoCredito
 * 
 * ⭐ CONCEITOS:
 * 1. IMPLEMENTAÇÃO DE INTERFACE - Fornece código concreto para interface
 * 2. POLIMORFISMO - É um MetodoPagamento
 * 3. ENCAPSULAMENTO - numeroCartao é private
 * 4. VALIDAÇÃO - Valida valor antes de processar
 * 
 * 📚 APRENDIZADO:
 * - implements MetodoPagamento = "eu sou um MetodoPagamento"
 * - @Override = "sobrescrevendo método da interface"
 * - Validações protegem o negócio
 * - Cada implementação tem sua lógica específica
 * 
 * 💼 QUANDO USAR:
 * - Pagamentos com cartão de crédito
 * - Sistema que aceita múltiplos métodos
 * - Strategy Pattern em ação
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
public class CartaoCredito implements MetodoPagamento {
    
    // =============== ENCAPSULAMENTO ===============
    // private = apenas esta classe acessa
    private String numeroCartao;
    
    /**
     * Construtor
     * 
     * @param numeroCartao Número do cartão (ex: "1234-5678-9999")
     */
    public CartaoCredito(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
    
    /**
     * ✅ IMPLEMENTAÇÃO DO MÉTODO DA INTERFACE
     * 
     * @Override = "estou substituindo método da superclasse/interface"
     */
    @Override
    public void processar(double valor) throws PagamentoException {
        // Validação 1: Valor positivo
        if (valor <= 0) {
            throw new PagamentoException(
                "❌ Erro: Valor deve ser positivo! Recebido: " + valor
            );
        }
        
        // Validação 2: Valor razoável (max R$ 100.000)
        if (valor > 100000) {
            throw new PagamentoException(
                "❌ Erro: Valor excede limite de R$ 100.000"
            );
        }
        
        // Simula autenticação do cartão
        if (!autenticarCartao()) {
            throw new PagamentoException(
                "❌ Erro: Cartão recusado"
            );
        }
        
        // Sucesso! Mostra mensagem
        System.out.println("✅ Pagamento com Cartão de Crédito processado!");
        System.out.println("   Cartão: " + numeroCartao);
        System.out.println("   Valor: R$ " + String.format("%.2f", valor));
        System.out.println("   Status: AUTORIZADO");
    }
    
    /**
     * Simula autenticação do cartão
     * 
     * @return true se autenticado
     */
    private boolean autenticarCartao() {
        // Em produção, chamaria API do banco
        // Aqui simulamos 95% de sucesso
        return Math.random() > 0.05;
    }
    
    // Getters (encapsulamento - permite ler, não modificar)
    public String getNumeroCartao() {
        return numeroCartao;
    }
}
