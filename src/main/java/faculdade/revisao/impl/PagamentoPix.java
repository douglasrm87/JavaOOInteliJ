package faculdade.revisao.impl;

import faculdade.revisao.exception.PagamentoException;
import faculdade.revisao.interfaces.MetodoPagamento;

/**
 * 📲 IMPLEMENTAÇÃO - PagamentoPix (com QR Code)
 * 
 * ⭐ CONCEITOS:
 * - IMPLEMENTAÇÃO DE INTERFACE (como PIX)
 * - POLIMORFISMO (igual PIX, mas com adicional)
 * - EXTENSÃO DE FUNCIONALIDADE (versão melhorada)
 * - PATTERN: DECORATOR (adiciona comportamento)
 * 
 * 📚 APRENDIZADO:
 * - Mesma interface, mas com feature adicional (QR Code)
 * - Mostra flexibilidade do polimorfismo
 * - Em produção, geraria QR Code real com biblioteca
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
public class PagamentoPix implements MetodoPagamento {
    
    private String chavePix;
    
    /**
     * Construtor
     * 
     * @param chavePix Chave PIX
     */
    public PagamentoPix(String chavePix) {
        this.chavePix = chavePix;
    }
    
    /**
     * ✅ IMPLEMENTAÇÃO - processar()
     * 
     * Adiciona: Geração de QR Code (visual feedback)
     */
    @Override
    public void processar(double valor) throws PagamentoException {
        // Mesmas validações do PIX básico
        if (valor <= 0) {
            throw new PagamentoException("Valor inválido!");
        }
        
        if (valor > 1000000) {
            throw new PagamentoException("Valor excede limite PIX!");
        }
        
        // DIFERENCIAL: Gera QR Code (simulado)
        String qrCode = gerarQrCode(valor);
        
        System.out.println("✅ PIX COM QR CODE - Processado!");
        System.out.println("   Valor: R$ " + String.format("%.2f", valor));
        System.out.println("   Chave: " + chavePix);
        System.out.println("   QR Code:\n" + qrCode);
        System.out.println("   Status: AGUARDANDO LEITURA DO QR");
    }
    
    /**
     * Simula geração de QR Code
     * 
     * Em produção usaria: https://zxing.org (Google)
     * 
     * @param valor Valor do pagamento
     * @return QR Code ASCII simulado
     */
    private String gerarQrCode(double valor) {
        return "┌────────────────────┐\n" +
               "│  ██████████████  │\n" +
               "│  ██            ██  │\n" +
               "│  ██  PIX ████  ██  │\n" +
               "│  ██  R$ " + String.format("%.0f", valor) + "  ██  │\n" +
               "│  ██            ██  │\n" +
               "│  ██████████████  │\n" +
               "└────────────────────┘";
    }
    
    public String getChavePix() {
        return chavePix;
    }
}
