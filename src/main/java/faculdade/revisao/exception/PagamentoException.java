package faculdade.revisao.exception;

/**
 * 🔴 EXCEÇÃO CUSTOMIZADA - PagamentoException
 * 
 * ⭐ CONCEITO: Tratamento de Exceções (Exception Handling)
 * 
 * Uma exceção customizada é lançada quando ocorrem erros específicos
 * em operações de pagamento. Permite tratamento granular de erros.
 * 
 * 📚 APRENDIZADO:
 * - Extends Exception → herança de exceção
 * - Permite criar exceções específicas do negócio
 * - Mais clara e profissional que genéricas
 * 
 * 💼 QUANDO USAR:
 * - Quando precisa tratamento específico de erro
 * - Valores inválidos de pagamento
 * - Saldo insuficiente
 * - Cartão recusado
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
public class PagamentoException extends Exception {
    
    /**
     * Construtor da exceção
     * 
     * @param mensagem Descrição do erro
     */
    public PagamentoException(String mensagem) {
        super(mensagem);
    }
    
    /**
     * Construtor com causa (causa raiz)
     * 
     * @param mensagem Descrição do erro
     * @param causa Exceção que causou este erro
     */
    public PagamentoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
