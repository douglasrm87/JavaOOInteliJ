package faculdade.revisao.model;

/**
 * 📦 CLASSE ABSTRATA - Produto
 * 
 * ⭐ CONCEITOS CHAVE:
 * 1. ABSTRAÇÃO - Define template, subclasses implementam detalhe
 * 2. HERANÇA - Subclasses herdam nome e preço
 * 3. ENCAPSULAMENTO - Atributos private + getters
 * 4. POLIMORFISMO - exibirDetalhes() diferentes em cada subclasse
 * 5. TEMPLATE METHOD - Força subclasses a implementarem método
 * 
 * 📚 APRENDIZADO:
 * - abstract class = não pode instanciar (só serve para herança)
 * - abstract method = subclasses DEVEM implementar
 * - "abstract" garante contrato de herança
 * - Todos os produtos (Camisa, Calça, Sapato) têm nome e preço
 * - Mas cada um exibe detalhes de forma diferente (polimorfismo!)
 * 
 * 💼 QUANDO USAR:
 * - Quando há conceito comum entre vários tipos
 * - Para garantir que subclasses implementem certos métodos
 * - Para compartilhar código comum (DRY - Don't Repeat Yourself)
 * 
 * 🔄 FLUXO:
 * Produto (abstrata)
 *   ↓ extends
 * Camisa (concreta)
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
public abstract class Produto {
    
    // =============== ENCAPSULAMENTO ===============
    // private = só acessível nesta classe
    private String nome;
    private double preco;
    
    /**
     * Construtor (protected = acessível por subclasses)
     * 
     * @param nome Nome do produto
     * @param preco Preço do produto
     */
    protected Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }
    
    /**
     * ✅ MÉTODO CONCRETO (tem implementação)
     * 
     * Compartilhado por todas as subclasses
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * ✅ MÉTODO CONCRETO
     */
    public double getPreco() {
        return preco;
    }
    
    /**
     * 🔴 MÉTODO ABSTRATO
     * 
     * - Não tem implementação (termina com ;)
     * - Subclasses DEVEM implementar (@Override)
     * - Garante que toda subclasse sabe exibir seus detalhes
     * - ISSO É POLIMORFISMO!
     * 
     * Camisa exibe: "Camisa Azul - Tamanho P - R$ 50"
     * Sapato exibe: "Nike - Tamanho 40 - R$ 200"
     * Etc...
     */
    public abstract void exibirDetalhes();
    
    /**
     * Método concreto que usa atributos comuns
     */
    public void exibirPreco() {
        System.out.println("Preço: R$ " + String.format("%.2f", preco));
    }
    
    /**
     * Atualiza preço (com validação)
     * 
     * @param novoPreco Novo preço
     */
    public void atualizarPreco(double novoPreco) {
        if (novoPreco > 0) {
            this.preco = novoPreco;
        }
    }
}
