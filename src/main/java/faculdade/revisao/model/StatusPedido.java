package faculdade.revisao.model;

/**
 * 📊 ENUM - StatusPedido
 * 
 * ⭐ CONCEITOS:
 * 1. ENUM - Type-safe constants (melhor que String ou int)
 * 2. SEGURANÇA - Só pode ter valores definidos
 * 3. LEGIBILIDADE - Código mais claro
 * 
 * 📚 APRENDIZADO:
 * - enum = tipo com valores pré-definidos
 * - Melhor que: String status = "PAGO"
 * - Melhor que: int status = 1
 * - Compiler verifica se valor é válido
 * - Mais rápido e eficiente
 * 
 * 💼 QUANDO USAR:
 * - Status de pedidos
 * - Estados possíveis de algo
 * - Valores que não mudam
 * - Quando quer evitar erros de digitação
 * 
 * 🔍 EXEMPLO:
 * ✅ StatusPedido.PAGO          // Seguro!
 * ❌ "PAGO"                      // Risco: erro de digitação
 * ❌ "pago"                      // Diferente de "PAGO"
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
public enum StatusPedido {
    
    // Valores possíveis do enum
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento", "⏳"),
    PAGO("Pago", "✅"),
    ENVIADO("Enviado", "📦"),
    ENTREGUE("Entregue", "🚚"),
    CANCELADO("Cancelado", "❌");
    
    // Cada enum pode ter atributos
    private String descricao;
    private String emoji;
    
    /**
     * Construtor do enum
     * 
     * @param descricao Descrição textual
     * @param emoji Emoji representativo
     */
    StatusPedido(String descricao, String emoji) {
        this.descricao = descricao;
        this.emoji = emoji;
    }
    
    // Getters
    public String getDescricao() {
        return descricao;
    }
    
    public String getEmoji() {
        return emoji;
    }
    
    /**
     * Retorna a representação completa
     */
    @Override
    public String toString() {
        return emoji + " " + descricao;
    }
}
