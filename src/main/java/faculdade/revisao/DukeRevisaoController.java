package faculdade.revisao;

import faculdade.revisao.impl.CartaoCredito;
import faculdade.revisao.impl.PIX;
import faculdade.revisao.impl.PagamentoPix;
import faculdade.revisao.model.Camisa;
import faculdade.revisao.model.StatusPedido;
import faculdade.revisao.service.Carrinho;
import faculdade.revisao.service.Pedido;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

/**
 * 🎮 CONTROLLER - DukeRevisaoController
 * 
 * ⭐ RESPONSABILIDADES:
 * 1. Gerenciar rotas do módulo revisão
 * 2. Orquestrar Model, Service e View (MVC)
 * 3. Manter estado da aplicação (sessão)
 * 4. Passar dados para templates via Model
 * 
 * 📚 APRENDIZADO:
 * - @Controller = classe que gerencia requisições
 * - @RequestMapping = prefixo de todas as rotas (/revisao/*)
 * - @GetMapping = mapear requisição GET
 * - @PostMapping = mapear requisição POST
 * - HttpSession = guardar dados entre requisições
 * - Model = passar dados para o template HTML
 * 
 * 💼 FLUXO MVC:
 * 1. Browser faz requisição GET /revisao/inicio
 * 2. Controller processa em inicio()
 * 3. Prepara Model com dados
 * 4. Retorna nome da view (template)
 * 5. Thymeleaf renderiza HTML
 * 6. Browser exibe resultado
 * 
 * @author Sistema Educacional
 * @version 1.0
 */
@Controller
@RequestMapping("/revisao")
public class DukeRevisaoController {
    
    /**
     * 📄 PÁGINA INICIAL - Duke Revisão
     */
    @GetMapping("/inicio")
    public String inicio(Model model) {
        model.addAttribute("titulo", "🏪 Duke Revisão - Loja Educacional");
        model.addAttribute("descricao", 
            "Aprenda POO com exemplo real de loja virtual");
        return "revisao/index";
    }
    
    /**
     * 📦 PÁGINA DE PRODUTOS
     */
    @GetMapping("/produtos")
    public String produtos(Model model) {
        // Em produção, viria de banco de dados
        model.addAttribute("titulo", "📦 Produtos");
        model.addAttribute("categoria", "Camisas");
        return "revisao/produtos";
    }
    
    /**
     * 🛒 PÁGINA DO CARRINHO
     */
    @GetMapping("/carrinho")
    public String carrinho(HttpSession session, Model model) {
        // Recupera carrinho da sessão, ou cria novo
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }
        
        model.addAttribute("titulo", "🛒 Meu Carrinho");
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", carrinho.calcularTotal());
        model.addAttribute("quantidade", carrinho.getQuantidadeItens());
        
        return "revisao/carrinho";
    }
    
    /**
     * ➕ ADICIONAR PRODUTO AO CARRINHO
     */
    @PostMapping("/adicionar-produto")
    public String adicionarProduto(
            @RequestParam String nome,
            @RequestParam double preco,
            @RequestParam String tamanho,
            HttpSession session) {
        
        // Recupera ou cria carrinho
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new Carrinho();
        }
        
        // Adiciona produto
        Camisa camisa = new Camisa(nome, preco, tamanho);
        carrinho.adicionarItem(camisa);
        
        // Salva na sessão
        session.setAttribute("carrinho", carrinho);
        
        return "redirect:/revisao/carrinho";
    }
    
    /**
     * 💳 PÁGINA DE PAGAMENTO
     */
    @GetMapping("/pagamento")
    public String pagamento(HttpSession session, Model model) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null || carrinho.getQuantidadeItens() == 0) {
            return "redirect:/revisao/carrinho";
        }
        
        double total = carrinho.calcularTotal();
        model.addAttribute("titulo", "💳 Escolha o Método de Pagamento");
        model.addAttribute("total", total);
        model.addAttribute("totalFormatado", 
            String.format("%.2f", total));
        
        return "revisao/pagamento";
    }
    
    /**
     * ✅ PROCESSAR PAGAMENTO
     */
    @PostMapping("/processar-pagamento")
    public String processarPagamento(
            @RequestParam String metodo,
            @RequestParam(value = "chave", defaultValue = "") String chave,
            HttpSession session,
            Model model) {
        
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null || carrinho.getQuantidadeItens() == 0) {
            return "redirect:/revisao/carrinho";
        }
        
        double total = carrinho.calcularTotal();
        Pedido pedido = new Pedido(carrinho);
        
        try {
            // Escolhe método de pagamento
            switch (metodo) {
                case "cartao":
                    pedido.fecharPedido(
                        new CartaoCredito("1234-5678-9999-0000"),
                        total
                    );
                    break;
                    
                case "pix":
                    pedido.fecharPedido(
                        new PIX("seu-pix@email.com"),
                        total
                    );
                    break;
                    
                case "pix-qrcode":
                    pedido.fecharPedido(
                        new PagamentoPix("sua-chave-aleatoria"),
                        total
                    );
                    break;
                    
                default:
                    throw new Exception("Método não suportado");
            }
            
            // Salva resultado na sessão
            session.setAttribute("pedido", pedido);
            session.setAttribute("metodo", metodo);
            
            // Limpa carrinho após sucesso
            if (pedido.getStatus() == StatusPedido.PAGO) {
                session.removeAttribute("carrinho");
            }
            
            model.addAttribute("pedido", pedido);
            model.addAttribute("metodo", metodo);
            model.addAttribute("sucesso", 
                pedido.getStatus() == StatusPedido.PAGO);
            
            return "revisao/confirmacao";
            
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            return "redirect:/revisao/pagamento";
        }
    }
    
    /**
     * 📚 PÁGINA DE CONCEITOS EDUCACIONAIS
     */
    @GetMapping("/conceitos")
    public String conceitos(Model model) {
        model.addAttribute("titulo", "📚 Conceitos POO");
        return "revisao/conceitos";
    }
    
    /**
     * 🔧 PÁGINA DE DIAGRAMA DE CLASSES
     */
    @GetMapping("/diagrama")
    public String diagrama(Model model) {
        model.addAttribute("titulo", "🔧 Diagrama de Classes");
        return "revisao/diagrama";
    }
}
