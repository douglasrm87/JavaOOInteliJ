package faculdade.revisao.impl;

import faculdade.revisao.exception.PagamentoException;
import faculdade.revisao.interfaces.MetodoPagamento;

/**
 * 📱 IMPLEMENTAÇÃO - PIX
 * 
 * ⭐ CONCEITOS:
 * - IMPLEMENTAÇÃO DE INTERFACE
 * - POLIMORFISMO (múltiplas formas de fazer a mesma coisa)
 * - VALIDAÇÃO DE NEGÓCIO
 * 
 * 📚 APRENDIZADO:
 * - PIX é um método diferente, mas implementa MESMA interface
 * - Cada implementação pode ter validações diferentes
 * - Polimorfismo permite trocar sem mudar código cliente
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
public class PIX implements MetodoPagamento {
    
    // ENCAPSULAMENTO - private
    private String numeroPIX;
    
    /**
     * Construtor
     * 
     * @param numeroPIX Chave PIX (pode ser CPF, email, telefone ou aleatória)
     */
    public PIX(String numeroPIX) {
        this.numeroPIX = numeroPIX;
    }
    
    /**
     * ✅ IMPLEMENTAÇÃO - processar()
     * 
     * Diferente de CartaoCredito:
     * - Sem limite de valor
     * - Processamento instantâneo
     * - Menos validações
     */
    @Override
    public void processar(double valor) throws PagamentoException {
        // Validação: Valor positivo
        if (valor <= 0) {
            throw new PagamentoException(
                "❌ Erro PIX: Valor deve ser positivo!"
            );
        }
        
        // PIX não tem limite (em teoria)
        // Mas vamos adicionar um limite alto para segurança
        if (valor > 1000000) {
            throw new PagamentoException(
                "❌ Erro PIX: Valor máximo é R$ 1.000.000"
            );
        }
        
        // Simula validação de chave PIX
        if (!validarChavePix()) {
            throw new PagamentoException(
                "❌ Erro: Chave PIX inválida"
            );
        }
        
        // Sucesso - PIX é instantâneo!
        System.out.println("✅ Pagamento PIX processado com sucesso!");
        System.out.println("   Chave: " + numeroPIX);
        System.out.println("   Valor: R$ " + String.format("%.2f", valor));
        System.out.println("   Status: REALIZADO (instantâneo)");
    }
    
    /**
     * Valida se chave PIX é válida
     * 
     * @return true se válida
     */
    private boolean validarChavePix() {
        // Em produção: validar com Banco Central
        // Aqui simulamos validação simples
        return numeroPIX != null && !numeroPIX.isEmpty();
    }
    
    public String getNumeroPIX() {
        return numeroPIX;
    }
}
