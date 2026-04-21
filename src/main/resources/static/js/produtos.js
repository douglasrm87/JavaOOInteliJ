/**
 * 📦 PRODUTOS.JS - Lógica da Página de Produtos
 * Duke Revisão - Sistema Educacional
 * 
 * Conceitos:
 * - DOM Manipulation
 * - Event Listeners
 * - LocalStorage (carrinho)
 * - JSON
 */

console.log("✅ produtos.js carregado!");

/**
 * Adiciona produto ao carrinho
 * 
 * @param {string} nome Nome do produto
 * @param {number} preco Preço do produto
 * @param {string} tamanho Tamanho selecionado
 */
function adicionarProduto(nome, preco, tamanho) {
    console.log(`➕ Adicionando: ${nome} - Tamanho ${tamanho} - R$ ${preco}`);
    
    // Recupera carrinho do localStorage
    let carrinho = localStorage.getItem("carrinho");
    carrinho = carrinho ? JSON.parse(carrinho) : [];
    
    // Cria novo item
    const item = {
        id: Date.now(), // ID único
        nome: nome,
        preco: preco,
        tamanho: tamanho,
        quantidade: 1
    };
    
    // Verifica se produto já existe
    const existe = carrinho.find(p => 
        p.nome === nome && p.tamanho === tamanho
    );
    
    if (existe) {
        existe.quantidade++;
        console.log(`📝 Quantidade atualizada: ${existe.quantidade}`);
    } else {
        carrinho.push(item);
        console.log(`✅ Produto adicionado ao carrinho!`);
    }
    
    // Salva carrinho
    localStorage.setItem("carrinho", JSON.stringify(carrinho));
    
    // Feedback visual
    mostrarNotificacao(`✅ ${nome} adicionado ao carrinho!`);
    
    // Atualiza badge do carrinho (se existir)
    atualizarBadgeCarrinho();
}

/**
 * Mostra notificação temporária
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
    
    // Remove após 3 segundos
    setTimeout(() => {
        notif.style.animation = "slideOut 0.3s ease";
        setTimeout(() => notif.remove(), 300);
    }, 3000);
}

/**
 * Atualiza badge de quantidade do carrinho
 */
function atualizarBadgeCarrinho() {
    const carrinho = JSON.parse(localStorage.getItem("carrinho") || "[]");
    const total = carrinho.reduce((sum, item) => sum + item.quantidade, 0);
    
    // Procura badge no HTML
    let badge = document.querySelector(".carrinho-badge");
    if (!badge) {
        badge = document.createElement("span");
        badge.className = "carrinho-badge";
        badge.style.cssText = `
            position: absolute;
            top: -8px;
            right: -8px;
            background: #6200EA;
            color: white;
            border-radius: 50%;
            width: 24px;
            height: 24px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 0.75rem;
            font-weight: 700;
        `;
        // Se encontrar link do carrinho, adiciona badge
        const linkCarrinho = document.querySelector('a[href="/revisao/carrinho"]');
        if (linkCarrinho) {
            linkCarrinho.style.position = "relative";
            linkCarrinho.appendChild(badge);
        }
    }
    
    badge.textContent = total > 0 ? total : "";
}

// Estilos de animação
const style = document.createElement("style");
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(400px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(400px);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);

// Inicializar
document.addEventListener("DOMContentLoaded", () => {
    console.log("🏪 Página de produtos iniciada");
    atualizarBadgeCarrinho();
});
