package faculdade.revisao.interfaces;

import faculdade.revisao.exception.PagamentoException;

/**
 * 🎯 INTERFACE - MetodoPagamento
 * 
 * ⭐ CONCEITOS:
 * 1. INTERFACE - Define um contrato que deve ser implementado
 * 2. POLIMORFISMO - Diferentes classes implementam o mesmo método
 * 3. STRATEGY PATTERN - Define família de algoritmos intercambiáveis
 * 4. CONTRATO DE INTERFACE - Garante implementação de métodos específicos
 * 
 * 📚 APRENDIZADO:
 * - Interface não tem implementação, só assinatura de métodos
 * - Todas as implementações DEVEM fazer `processar()`
 * - Permite trocar estratégia em tempo de execução
 * - @FunctionalInterface = interface com um único método abstrato
 * 
 * 💼 QUANDO USAR:
 * - Quando há múltiplas formas de fazer algo (ex: pagar)
 * - Quando quer trocar comportamento sem mudar código
 * - Para garantir contrato entre classes
 * 
 * 🔄 POLIMORFISMO EM AÇÃO:
 * Pedido.fecharPedido() não precisa saber QUAL tipo de pagamento,
 * só sabe que tem método processar(). Magic! ✨
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
@FunctionalInterface // ← Anotação: indica interface com 1 método abstrato
public interface MetodoPagamento {
    
    /**
     * Processa um pagamento com o valor especificado
     * 
     * ⚠️ IMPORTANTE:
     * - Lança PagamentoException se falhar
     * - Implementações devem validar o valor
     * - Implementações devem fazer validações de segurança
     * 
     * @param valor Valor a ser pago
     * @throws PagamentoException Se o pagamento falhar
     */
    void processar(double valor) throws PagamentoException;
}
