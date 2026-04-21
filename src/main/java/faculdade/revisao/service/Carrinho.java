package faculdade.revisao.service;

import faculdade.revisao.model.Produto;
import java.util.ArrayList;
import java.util.List;

/**
 * 🛒 CLASSE - Carrinho
 * 
 * ⭐ CONCEITOS:
 * 1. AGREGAÇÃO - Carrinho "tem muitos" Produtos
 * 2. COLEÇÕES - ArrayList para armazenar múltiplos itens
 * 3. STREAMS - Programação funcional para cálculos
 * 4. POLIMORFISMO - Lista contém qualquer subclasse de Produto
 * 
 * 📚 APRENDIZADO:
 * - ArrayList<Produto> = pode conter Camisa, Sapato, etc (polimorfismo!)
 * - Stream API = forma elegante de iterar e calcular
 * - .mapToDouble() = converte para números
 * - .sum() = soma todos os valores
 * - Padrão: "Utility Class" para operações sobre coleção
 * 
 * 💼 QUANDO USAR:
 * - Armazenar múltiplos itens
 * - Calcular totais
 * - Gerenciar coleções de objetos
 * 
 * 🔄 FLUXO:
 * Carrinho
 *   ├─ Camisa (tamanho P)
 *   ├─ Camisa (tamanho M)
 *   ├─ Sapato (tamanho 42)
 *   └─ calcularTotal() = soma todos
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
public class Carrinho {
    
    // AGREGAÇÃO: Carrinho contém muitos Produtos
    private List<Produto> itens;
    
    /**
     * Construtor
     */
    public Carrinho() {
        this.itens = new ArrayList<>();
    }
    
    /**
     * Adiciona um produto ao carrinho
     * 
     * Polimorfismo em ação:
     * - Aceita Camisa
     * - Aceita Sapato
     * - Aceita qualquer subclasse de Produto!
     * 
     * @param produto Qualquer subclasse de Produto
     */
    public void adicionarItem(Produto produto) {
        if (produto != null) {
            itens.add(produto);
            System.out.println("✅ Adicionado: " + produto.getNome());
        }
    }
    
    /**
     * Remove um produto do carrinho
     * 
     * @param produto Produto a remover
     */
    public void removerItem(Produto produto) {
        if (itens.remove(produto)) {
            System.out.println("✅ Removido: " + produto.getNome());
        }
    }
    
    /**
     * Calcula o valor total do carrinho
     * 
     * ⭐ STREAMS API EM AÇÃO:
     * .stream() = converte lista em stream
     * .mapToDouble() = extrai preço de cada produto (double)
     * .sum() = soma todos os preços
     * 
     * Sem Streams (forma antiga):
     * double total = 0;
     * for (Produto p : itens) {
     *   total += p.getPreco();
     * }
     * 
     * Com Streams (forma moderna):
     * itens.stream().mapToDouble(Produto::getPreco).sum()
     * 
     * @return Total em reais
     */
    public double calcularTotal() {
        return itens.stream()
                    .mapToDouble(Produto::getPreco)
                    .sum();
    }
    
    /**
     * Retorna quantidade de itens
     */
    public int getQuantidadeItens() {
        return itens.size();
    }
    
    /**
     * Retorna lista de itens
     */
    public List<Produto> getItens() {
        return new ArrayList<>(itens); // Retorna cópia para segurança
    }
    
    /**
     * Limpa o carrinho
     */
    public void limpar() {
        itens.clear();
        System.out.println("✅ Carrinho limpo!");
    }
    
    /**
     * Exibe conteúdo do carrinho
     */
    public void exibirCarrinho() {
        if (itens.isEmpty()) {
            System.out.println("🛒 Carrinho vazio!");
            return;
        }
        
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║         🛒 SEU CARRINHO          ║");
        System.out.println("╠═══════════════════════════════════╣");
        
        for (int i = 0; i < itens.size(); i++) {
            Produto p = itens.get(i);
            System.out.println("║ " + (i+1) + ". " + p.getNome() + 
                              " - R$ " + String.format("%.2f", p.getPreco()));
        }
        
        System.out.println("╠═══════════════════════════════════╣");
        System.out.println("║ TOTAL: R$ " + 
                          String.format("%.2f", calcularTotal()) + 
                          "                    ║");
        System.out.println("╚═══════════════════════════════════╝");
    }
}
