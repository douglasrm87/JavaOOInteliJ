/**
 * 🛒 CARRINHO.JS - Lógica da Página de Carrinho
 * Duke Revisão - Sistema Educacional
 */

console.log("✅ carrinho.js carregado!");

/**
 * Carrega e exibe itens do carrinho
 */
function carregarCarrinho() {
    console.log("🛒 Carregando carrinho...");
    
    const carrinho = JSON.parse(localStorage.getItem("carrinho") || "[]");
    const container = document.getElementById("carrinhoContainer");
    const tabela = document.getElementById("carrinhoTabela");
    const resumo = document.getElementById("resumoContainer");
    const acoes = document.getElementById("acoesContainer");
    const tbody = document.getElementById("tabelaItens");
    
    if (carrinho.length === 0) {
        // Carrinho vazio
        container.style.display = "flex";
        tabela.style.display = "none";
        resumo.style.display = "none";
        acoes.style.display = "none";
        return;
    }
    
    // Carrinho com itens
    container.style.display = "none";
    tabela.style.display = "block";
    resumo.style.display = "block";
    acoes.style.display = "flex";
    
    // Limpa tabela
    tbody.innerHTML = "";
    
    // Preenche tabela
    let total = 0;
    carrinho.forEach((item, index) => {
        const subtotal = item.preco * item.quantidade;
        total += subtotal;
        
        const row = document.createElement("tr");
        row.innerHTML = `
            <td><strong>${item.nome}</strong></td>
            <td>${item.tamanho}</td>
            <td>R$ ${item.preco.toFixed(2)}</td>
            <td>
                <button class="btn-remove" onclick="removerItem(${index})">
                    🗑️ Remover
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
    
    // Atualiza resumo
    document.getElementById("quantidadeItens").textContent = carrinho.length;
    document.getElementById("subtotal").textContent = `R$ ${total.toFixed(2)}`;
    
    const frete = 10.00; // Frete fixo
    document.getElementById("frete").textContent = `R$ ${frete.toFixed(2)}`;
    
    const totalFinal = total + frete;
    document.getElementById("total").textContent = `R$ ${totalFinal.toFixed(2)}`;
}

/**
 * Remove item do carrinho
 * 
 * @param {number} index Índice do item
 */
function removerItem(index) {
    console.log(`🗑️ Removendo item ${index}...`);
    
    let carrinho = JSON.parse(localStorage.getItem("carrinho") || "[]");
    const nome = carrinho[index].nome;
    
    carrinho.splice(index, 1);
    localStorage.setItem("carrinho", JSON.stringify(carrinho));
    
    console.log(`✅ Item removido: ${nome}`);
    carregarCarrinho();
    mostrarNotificacao(`✅ ${nome} removido do carrinho!`);
}

/**
 * Limpa carrinho completamente
 */
function limparCarrinho() {
    if (confirm("⚠️  Deseja realmente limpar o carrinho?")) {
        localStorage.removeItem("carrinho");
        console.log("🗑️ Carrinho limpo!");
        carregarCarrinho();
        mostrarNotificacao("✅ Carrinho limpo!");
    }
}

/**
 * Mostra notificação
 */
function mostrarNotificacao(mensagem) {
    const notif = document.createElement("div");
    notif.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        background: linear-gradient(135deg, #6200EA 0%, #03DAC6 100%);
        color: white;
        padding: 1rem 1.5rem;
        border-radius: 6px;
        box-shadow: 0 6px 16px rgba(98, 0, 234, 0.3);
        z-index: 9999;
        animation: slideIn 0.3s ease;
    `;
    notif.textContent = mensagem;
    document.body.appendChild(notif);
    
    setTimeout(() => {
        notif.style.animation = "slideOut 0.3s ease";
        setTimeout(() => notif.remove(), 300);
    }, 3000);
}

// Inicializar
document.addEventListener("DOMContentLoaded", () => {
    console.log("🛒 Página do carrinho iniciada");
    carregarCarrinho();
});
