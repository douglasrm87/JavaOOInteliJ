/**
 * 🎮 REVISAO.JS - Lógica Geral do Módulo Revisão
 * Duke Revisão - Sistema Educacional
 */

console.log("✅ revisao.js carregado!");

/**
 * Inicializa animações e interatividades
 */
function inicializarAnimacoes() {
    // Anima cards ao entrar na viewport
    const cards = document.querySelectorAll(".op-card, .product-card, .metodo-card");
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.animation = "fadeIn 0.6s ease forwards";
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.1 });
    
    cards.forEach(card => observer.observe(card));
}

/**
 * Smooth scroll para links âncora
 */
function inicializarSmoothScroll() {
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({ behavior: 'smooth' });
            }
        });
    });
}

/**
 * Destaca código ao copiar
 */
function inicializarCodeHighlight() {
    const codeBlocks = document.querySelectorAll(".code-example pre");
    
    codeBlocks.forEach(block => {
        // Adiciona botão de copiar
        const btnCopiar = document.createElement("button");
        btnCopiar.textContent = "📋 Copiar";
        btnCopiar.className = "btn-copy";
        btnCopiar.style.cssText = `
            position: absolute;
            top: 10px;
            right: 10px;
            background: #6200EA;
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.85rem;
            z-index: 10;
        `;
        
        block.parentElement.style.position = "relative";
        block.parentElement.appendChild(btnCopiar);
        
        btnCopiar.addEventListener("click", () => {
            const codigo = block.textContent;
            navigator.clipboard.writeText(codigo).then(() => {
                const textoOriginal = btnCopiar.textContent;
                btnCopiar.textContent = "✅ Copiado!";
                setTimeout(() => {
                    btnCopiar.textContent = textoOriginal;
                }, 2000);
            });
        });
    });
}

/**
 * Log de navegação para debug
 */
function logNavegacao() {
    const pagina = window.location.pathname;
    console.log(`📄 Página: ${pagina}`);
    
    // Destaca link ativo na navegação
    const links = document.querySelectorAll("nav a");
    links.forEach(link => {
        if (link.getAttribute("href") === pagina) {
            link.style.borderBottom = "3px solid #6200EA";
        }
    });
}

/**
 * Mostra versão da app
 */
function mostrarInfo() {
    console.log(`
    🏪 DUKE REVISÃO - Sistema Educacional
    Versão: 1.0
    Desenvolvido com: Spring Boot + Thymeleaf
    Conceitos: POO, Design Patterns, MVC
    
    📚 Aprendendo com: Polimorfismo, Herança, Abstração, Encapsulamento
    `);
}

/**
 * Tratamento de erros global
 */
window.addEventListener("error", (event) => {
    console.error("❌ Erro capturado:", event.error);
});

/**
 * Detecta conexão offline
 */
window.addEventListener("offline", () => {
    console.warn("⚠️  Modo offline ativado");
    alert("⚠️  Você está offline. Algumas funcionalidades podem não funcionar.");
});

window.addEventListener("online", () => {
    console.log("✅ Conexão restaurada");
});

// Inicializar tudo quando documento estiver pronto
document.addEventListener("DOMContentLoaded", () => {
    console.log("🎮 Inicializando Duke Revisão...");
    
    inicializarAnimacoes();
    inicializarSmoothScroll();
    inicializarCodeHighlight();
    logNavegacao();
    mostrarInfo();
    
    console.log("✅ Duke Revisão inicializado com sucesso!");
});
