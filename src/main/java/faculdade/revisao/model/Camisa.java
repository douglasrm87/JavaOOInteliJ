package faculdade.revisao.model;

/**
 * 👕 CLASSE CONCRETA - Camisa
 * 
 * ⭐ CONCEITOS:
 * 1. HERANÇA - extends Produto
 * 2. POLIMORFISMO - implementa exibirDetalhes() de forma específica
 * 3. @Override - substitui método da superclasse
 * 4. EXTENSÃO - adiciona atributo específico (tamanho)
 * 
 * 📚 APRENDIZADO:
 * - extends = herda tudo de Produto (nome, preco, getNome(), etc)
 * - super() = chama construtor da superclasse
 * - tamanho = atributo específico de Camisa
 * - exibirDetalhes() = OBRIGADO a implementar (método abstrato)
 * - Cada subclasse implementa diferente! (polimorfismo)
 * 
 * 💼 EXEMPLO:
 * - Produto não sabe exibir detalhes (é abstrato)
 * - Camisa sabe: "Camisa Azul - P - R$ 50"
 * - Se tivesse Sapato, exibiria: "Nike Air - 42 - R$ 200"
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
public class Camisa extends Produto {
    
    // Atributo específico de Camisa
    private String tamanho; // P, M, G, GG
    
    /**
     * Construtor
     * 
     * super() = chama construtor de Produto
     * Isso inicializa nome e preco (herança em ação!)
     * 
     * @param nome Nome da camisa
     * @param preco Preço da camisa
     * @param tamanho Tamanho (P, M, G, GG)
     */
    public Camisa(String nome, double preco, String tamanho) {
        super(nome, preco); // Inicializa atributos de Produto
        this.tamanho = tamanho;
    }
    
    /**
     * ✅ IMPLEMENTAÇÃO DO MÉTODO ABSTRATO
     * 
     * @Override = "estou substituindo método de Produto"
     * 
     * POLIMORFISMO EM AÇÃO:
     * Mesmo método, comportamento diferente!
     */
    @Override
    public void exibirDetalhes() {
        // Usa métodos herdados de Produto
        System.out.println("═══════════════════════════════");
        System.out.println("👕 CAMISA");
        System.out.println("═══════════════════════════════");
        System.out.println("Nome: " + getNome());
        System.out.println("Tamanho: " + tamanho);
        System.out.println("Preço: R$ " + String.format("%.2f", getPreco()));
        System.out.println("═══════════════════════════════");
    }
    
    /**
     * Método específico de Camisa
     */
    public void aplicarDesconto(double percentual) {
        double desconto = getPreco() * (percentual / 100);
        double novoPreco = getPreco() - desconto;
        atualizarPreco(novoPreco);
        System.out.println("✅ Desconto aplicado! Novo preço: R$ " + 
                          String.format("%.2f", novoPreco));
    }
    
    // Getters
    public String getTamanho() {
        return tamanho;
    }
    
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
