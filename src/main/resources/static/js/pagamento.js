/**
 * 💳 PAGAMENTO.JS - Lógica da Página de Pagamento
 * Duke Revisão - Sistema Educacional
 */

console.log("✅ pagamento.js carregado!");

/**
 * Validar formulário de pagamento antes de enviar
 */
function validarPagamento(event) {
    console.log("💳 Validando pagamento...");
    
    const metodo = event.target.querySelector('input[name="metodo"]').value;
    console.log(`Método selecionado: ${metodo}`);
    
    // Recupera carrinho
    const carrinho = JSON.parse(localStorage.getItem("carrinho") || "[]");
    
    if (carrinho.length === 0) {
        alert("❌ Carrinho vazio! Adicione produtos antes de pagar.");
        return false;
    }
    
    const total = carrinho.reduce((sum, item) => sum + (item.preco * item.quantidade), 0);
    console.log(`✅ Total a pagar: R$ ${total.toFixed(2)}`);
    
    // Simula processamento
    console.log(`💳 Processando pagamento com ${metodo}...`);
    
    return true;
}

/**
 * Mostra detalhes do método de pagamento
 */
function mostrarDetalhes(metodo) {
    console.log(`📋 Detalhes de: ${metodo}`);
    
    const detalhes = {
        cartao: {
            titulo: "💳 Cartão de Crédito",
            descricao: "Pagamento seguro com parcelamento em até 12x",
            features: ["✅ Seguro", "✅ Parcelado", "✅ Instantâneo"]
        },
        pix: {
            titulo: "📱 PIX Instantâneo",
            descricao: "Transferência bancária imediata, sem taxas",
            features: ["✅ Sem taxa", "✅ Instantâneo", "✅ 24/7"]
        },
        pix_qrcode: {
            titulo: "📲 PIX com QR Code",
            descricao: "Escaneia o QR Code com seu telefone",
            features: ["✅ QR Code", "✅ Mais seguro", "✅ Moderno"]
        }
    };
    
    const info = detalhes[metodo];
    if (info) {
        alert(`${info.titulo}\n\n${info.descricao}\n\nFeatures: ${info.features.join(" ")}`);
    }
}

/**
 * Animação de processamento
 */
function animarProcessamento() {
    const buttons = document.querySelectorAll(".btn-payment");
    buttons.forEach(btn => {
        btn.addEventListener("click", function() {
            const textoOriginal = this.textContent;
            this.textContent = "⏳ Processando...";
            this.disabled = true;
            
            setTimeout(() => {
                this.textContent = textoOriginal;
                this.disabled = false;
            }, 2000);
        });
    });
}

/**
 * Mostra tooltip com conceito
 */
function mostrarConceito(elemento) {
    const tooltip = elemento.querySelector(".concept-note");
    if (tooltip) {
        tooltip.style.display = tooltip.style.display === "none" ? "block" : "none";
    }
}

// Inicializar
document.addEventListener("DOMContentLoaded", () => {
    console.log("💳 Página de pagamento iniciada");
    animarProcessamento();
});
