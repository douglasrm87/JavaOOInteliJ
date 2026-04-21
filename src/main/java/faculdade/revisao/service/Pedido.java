package faculdade.revisao.service;

import faculdade.revisao.exception.PagamentoException;
import faculdade.revisao.interfaces.MetodoPagamento;
import faculdade.revisao.model.StatusPedido;

/**
 * 📋 CLASSE - Pedido
 * 
 * ⭐ CONCEITOS CHAVE:
 * 1. COMPOSIÇÃO - Pedido contém Carrinho
 * 2. POLIMORFISMO - Aceita qualquer MetodoPagamento (CartaoCredito, PIX, etc)
 * 3. TRATAMENTO DE EXCEÇÕES - try-catch para erros de pagamento
 * 4. STATE PATTERN - Status do pedido muda conforme operações
 * 
 * 📚 APRENDIZADO:
 * 🎯 POLIMORFISMO EM AÇÃO (O MAGIC ACONTECE AQUI!)
 * 
 * fecharPedido() recebe interface MetodoPagamento
 * Não importa se é CartaoCredito, PIX ou PagamentoPix
 * Só chama metodo.processar() - polimorfismo faz o resto!
 * 
 * Código desacoplado: não precisa saber implementação
 * Fácil adicionar novo método de pagamento (Strategy Pattern)
 * 
 * 💼 QUANDO USAR:
 * - Orquestrar operações complexas
 * - Gerenciar estado
 * - Integrar múltiplas partes do sistema
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
public class Pedido {
    
    // COMPOSIÇÃO: Pedido contém um Carrinho
    private Carrinho carrinho;
    private StatusPedido status;
    private double valorTotal;
    
    /**
     * Construtor
     * 
     * @param carrinho Carrinho com produtos
     */
    public Pedido(Carrinho carrinho) {
        this.carrinho = carrinho;
        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
        this.valorTotal = carrinho.calcularTotal();
    }
    
    /**
     * 🎯 MÉTODO CHAVE - POLIMORFISMO EM AÇÃO
     * 
     * ⭐ ESTE É O PADRÃO STRATEGY EM AÇÃO!
     * 
     * Aceita QUALQUER implementação de MetodoPagamento:
     * - CartaoCredito
     * - PIX
     * - PagamentoPix
     * - Boleto (se implementar)
     * - Débito (se implementar)
     * - Etc...
     * 
     * Sem mudar código! Isso é polimorfismo/strategy pattern!
     * 
     * @param metodo Qualquer implementação de MetodoPagamento
     * @param valor Valor do pagamento
     */
    public void fecharPedido(MetodoPagamento metodo, double valor) {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║  🛍️  PROCESSANDO PAGAMENTO          ║");
        System.out.println("╚═════════════════════════════════════╝\n");
        
        try {
            // Validações básicas
            if (valor <= 0) {
                throw new PagamentoException("Valor deve ser positivo!");
            }
            
            if (valor != valorTotal) {
                System.out.println("⚠️  Aviso: Valor diferente do total!");
                System.out.println("   Esperado: R$ " + String.format("%.2f", valorTotal));
                System.out.println("   Recebido: R$ " + String.format("%.2f", valor));
            }
            
            // ✅ POLIMORFISMO AQUI!
            // Não importa qual tipo é metodo, ele tem processar()!
            metodo.processar(valor);
            
            // Se chegou aqui, pagamento foi bem-sucedido
            status = StatusPedido.PAGO;
            System.out.println("\n✅ PEDIDO CONFIRMADO!");
            System.out.println("   Status: " + status);
            System.out.println("   Valor: R$ " + String.format("%.2f", valor));
            
        } catch (PagamentoException e) {
            // Se deu erro, permanece em AGUARDANDO_PAGAMENTO
            status = StatusPedido.AGUARDANDO_PAGAMENTO;
            System.out.println("\n❌ ERRO: " + e.getMessage());
            System.out.println("   Pedido não foi confirmado!");
            System.out.println("   Tente novamente com outro método.");
            
        } catch (Exception e) {
            // Erro genérico
            System.out.println("❌ Erro inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Exibe resumo do pedido
     */
    public void exibirResumo() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║     📋 RESUMO DO PEDIDO             ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ Status: " + status.getEmoji() + " " + status.getDescricao());
        System.out.println("║ Itens: " + carrinho.getQuantidadeItens());
        System.out.println("║ Total: R$ " + String.format("%.2f", valorTotal));
        System.out.println("╚═════════════════════════════════════╝\n");
    }
    
    // Getters
    public Carrinho getCarrinho() {
        return carrinho;
    }
    
    public StatusPedido getStatus() {
        return status;
    }
    
    public double getValorTotal() {
        return valorTotal;
    }
}
