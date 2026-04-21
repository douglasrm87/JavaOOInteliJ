package faculdade.login;

import faculdade.login.repository.ClienteDAO;
import faculdade.login.repository.ContaCorrenteDAO;
import faculdade.login.repository.TransacaoDAO;
import faculdade.login.service.OperacaoBancariaService;
import model.entidades.Cliente;
import model.entidades.ContaCorrente;
import model.entidades.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador de Rotas da Aplicação
 * 
 * Esta classe centraliza todas as rotas (endpoints) da aplicação.
 * Implementa a interface WebMvcConfigurer para configurações específicas.
 * 
 * Padrão de Design: MVC (Model-View-Controller)
 * - Controller: Esta classe (recebe requisições)
 * - Model: Entidades (Cliente, ContaCorrente, etc)
 * - View: Templates HTML (Thymeleaf)
 * 
 * Padrão de Requisição:
 * @RequestMapping: Define a rota e o método HTTP (GET, POST)
 * @ModelAttribute: Mapeia automaticamente dados do formulário para objetos
 * Model/ModelMap: Passa dados para a view (template HTML)
 */
@Controller
public class MinhasRotas implements WebMvcConfigurer {
    
    // Dependências injetadas pelo Spring (IoC - Inversion of Control)
    @Autowired
    private ClienteDAO clienteRepository;

    @Autowired
    private ContaCorrenteDAO contaCorrenteDAO;

    @Autowired
    private TransacaoDAO transacaoDAO;

    @Autowired
    private OperacaoBancariaService operacaoBancariaService;

    /**
     * Configuração de visualizações padrão
     * Mapeia URLs para templates sem processar lógica
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

    // ============================================
    // ROTAS DO SISTEMA DE VENDAS (Existentes)
    // ============================================

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String carregarHello(ModelMap model) {
        System.out.println("Acionado método carregarHello().");
        model.addAttribute("nomeCasa", "Tela Hello carregada.");
        return "hello";
    }

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public String carregarLogin(ModelMap model) {
        System.out.println("Acionado método carregarLogin().");
        return "login";
    }

    @RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
    public String carregarLogout(ModelMap model) {
        System.out.println("Acionado método carregarLogout().");
        return "login"; // Redireciona para login após logout
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String carregarHome(ModelMap model) {
        System.out.println("Acionado método carregarHome().");
        model.addAttribute("nomeCasa", "Bem-vindo ao Sistema Bancário");
        return "home";
    }

    @RequestMapping(value = "/pedido", method = RequestMethod.GET)
    public String criarPedido(ModelMap model) {
        System.out.println("Acionado método criarPedido().");
        model.addAttribute("nomeCasa", "Carregar a tela de Pedido.");
        return "pedido";
    }

    @RequestMapping(value = "/cadastro", method = { RequestMethod.GET, RequestMethod.POST })
    public String salvarClienteController(@ModelAttribute Cliente cli, Model padrao) {
        System.out.println("Acionado método salvarClienteController().");
        padrao.addAttribute("cliente", new Cliente("Digite o nome aqui"));
        
        // Se o nome foi preenchido, salvar no banco
        if (cli.getNome() != null && !cli.getNome().isEmpty()) {
            System.out.println("Nome digitado no formulário: " + cli.getNome());
            clienteRepository.salvarCliente(cli);
        }
        
        return "cadastro";
    }

    @RequestMapping(value = "/consulta", method = { RequestMethod.GET, RequestMethod.POST })
    public String listarClienteController(@ModelAttribute Cliente cli, Model padrao) {
        System.out.println("Acionado método listarClienteController().");
        padrao.addAttribute("cliente", new Cliente());
        
        if (cli.getNome() != null && !cli.getNome().isEmpty()) {
            System.out.println("Nome digitado para consulta: " + cli.getNome());
            List<Cliente> cliS = clienteRepository.obterClienteByNome(cli.getNome());
            
            if (cliS != null && cliS.size() > 0 && cliS.get(0).getNome().length() > 0) {
                System.out.println("Dados retornados.");
                padrao.addAttribute("cliente", cliS.get(0));
                System.out.println("Dados selecionados: " + cliS.get(0).getId() + " - " + cliS.get(0).getNome());
            } else {
                padrao.addAttribute("cliente", new Cliente());
            }
        }
        
        return "consulta";
    }

    // ============================================
    // ROTAS DO SISTEMA BANCÁRIO (Novo)
    // ============================================

    /**
     * Menu principal do caixa eletrônico
     */
    @RequestMapping(value = "/banco/frentecaixaeletronico", method = RequestMethod.GET)
    public String carregarFrenteCaixaEletronico(ModelMap model) {
        System.out.println("Acionado método carregarFrenteCaixaEletronico().");
        model.addAttribute("nomeCasa", "Frente de Caixa Eletrônico");
        return "/banco/frentecaixaeletronico";
    }

    /**
     * Página de cadastro de conta corrente
     * GET: Exibe formulário vazio
     * POST: Processa cadastro
     */
    @RequestMapping(value = "/banco/cadastrocontacorrente", method = RequestMethod.GET)
    public String carregarCadastroConta(ModelMap model) {
        System.out.println("Acionado método carregarCadastroConta() - GET");
        return "/banco/cadastrocontacorrente";
    }

    @RequestMapping(value = "/banco/cadastrocontacorrente", method = RequestMethod.POST)
    public String processarCadastroConta(
            @RequestParam String numeroConta,
            @RequestParam String agencia,
            @RequestParam String cpf,
            @RequestParam(required = false) BigDecimal saldoInicial,
            ModelMap model) {
        System.out.println("Acionado método processarCadastroConta() - POST");
        System.out.println("Dados recebidos - Conta: " + numeroConta + ", Agência: " + agencia + ", CPF: " + cpf);
        
        try {
            String resultado = operacaoBancariaService.cadastrarContaCorrente(numeroConta, agencia, cpf, saldoInicial);
            model.addAttribute("mensagem", resultado);
            model.addAttribute("sucesso", true);
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cadastrar conta: " + e.getMessage());
            System.out.println("Erro ao cadastrar conta: " + e.getMessage());
        }
        
        return "/banco/cadastrocontacorrente";
    }

    /**
     * Página de saque
     * GET: Exibe formulário
     * POST: Processa saque
     */
    @RequestMapping(value = "/banco/saque", method = RequestMethod.GET)
    public String carregarSaque(ModelMap model) {
        System.out.println("Acionado método carregarSaque() - GET");
        return "/banco/saque";
    }

    @RequestMapping(value = "/banco/saque", method = RequestMethod.POST)
    public String processarSaque(
            @RequestParam String numeroConta,
            @RequestParam String agencia,
            @RequestParam String cpf,
            @RequestParam BigDecimal valor,
            ModelMap model) {
        System.out.println("Acionado método processarSaque() - POST");
        System.out.println("Dados: Conta=" + numeroConta + ", Valor=" + valor);
        
        try {
            String resultado = operacaoBancariaService.realizarSaque(numeroConta, agencia, cpf, valor);
            
            if (resultado.contains("Erro")) {
                model.addAttribute("erro", resultado);
            } else {
                model.addAttribute("mensagem", resultado);
            }
            
            // Mostrar dados da conta novamente após operação
            ContaCorrente conta = operacaoBancariaService.consultarSaldo(numeroConta, agencia, cpf);
            if (conta != null) {
                model.addAttribute("saldoAtual", conta.getSaldo());
            }
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao processar saque: " + e.getMessage());
            System.out.println("Erro ao processar saque: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "/banco/saque";
    }

    /**
     * Página de depósito
     * GET: Exibe formulário
     * POST: Processa depósito
     */
    @RequestMapping(value = "/banco/deposito", method = RequestMethod.GET)
    public String carregarDeposito(ModelMap model) {
        System.out.println("Acionado método carregarDeposito() - GET");
        return "/banco/deposito";
    }

    @RequestMapping(value = "/banco/deposito", method = RequestMethod.POST)
    public String processarDeposito(
            @RequestParam String numeroConta,
            @RequestParam String agencia,
            @RequestParam String cpf,
            @RequestParam BigDecimal valor,
            @RequestParam(required = false) String descricao,
            ModelMap model) {
        System.out.println("Acionado método processarDeposito() - POST");
        System.out.println("Dados: Conta=" + numeroConta + ", Valor=" + valor);
        
        try {
            String resultado = operacaoBancariaService.realizarDeposito(numeroConta, agencia, cpf, valor, descricao);
            
            if (resultado.contains("Erro")) {
                model.addAttribute("erro", resultado);
            } else {
                model.addAttribute("mensagem", resultado);
            }
            
            // Mostrar dados da conta novamente após operação
            ContaCorrente conta = operacaoBancariaService.consultarSaldo(numeroConta, agencia, cpf);
            if (conta != null) {
                model.addAttribute("saldoAtual", conta.getSaldo());
            }
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao processar depósito: " + e.getMessage());
            System.out.println("Erro ao processar depósito: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "/banco/deposito";
    }

    /**
     * Página de consulta de extrato
     * GET: Exibe formulário
     * POST: Consulta e exibe extrato
     */
    @RequestMapping(value = "/banco/extrato", method = RequestMethod.GET)
    public String carregarExtrato(ModelMap model) {
        System.out.println("Acionado método carregarExtrato() - GET");
        model.addAttribute("exibindo", false);
        return "/banco/extrato";
    }

    @RequestMapping(value = "/banco/extrato", method = RequestMethod.POST)
    public String processarExtrato(
            @RequestParam String numeroConta,
            @RequestParam String agencia,
            @RequestParam String cpf,
            ModelMap model) {
        System.out.println("Acionado método processarExtrato() - POST");
        System.out.println("Consultando extrato da conta: " + numeroConta);
        
        try {
            ContaCorrente conta = operacaoBancariaService.consultarSaldo(numeroConta, agencia, cpf);
            
            if (conta == null) {
                model.addAttribute("erro", "Conta não encontrada ou dados inválidos.");
                model.addAttribute("exibindo", false);
            } else {
                // Carregar dados da conta
                model.addAttribute("nomeCliente", "Cliente da Conta");
                model.addAttribute("numeroConta", conta.getNumeroConta());
                model.addAttribute("agencia", conta.getAgencia());
                model.addAttribute("saldo", String.format("%.2f", conta.getSaldo()));
                
                // Carregar transações
                List<Transacao> transacoes = operacaoBancariaService.obterHistoricoTransacoes(conta.getId());
                model.addAttribute("transacoes", transacoes);
                model.addAttribute("exibindo", true);
            }
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao consultar extrato: " + e.getMessage());
            model.addAttribute("exibindo", false);
            System.out.println("Erro ao consultar extrato: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "/banco/extrato";
    }

    /**
     * Página de consulta de saldo
     * GET: Exibe formulário
     * POST: Consulta e exibe saldo
     */
    @RequestMapping(value = "/banco/saldo", method = RequestMethod.GET)
    public String carregarSaldo(ModelMap model) {
        System.out.println("Acionado método carregarSaldo() - GET");
        model.addAttribute("exibindo", false);
        return "/banco/saldo";
    }

    @RequestMapping(value = "/banco/saldo", method = RequestMethod.POST)
    public String processarSaldo(
            @RequestParam String numeroConta,
            @RequestParam String agencia,
            @RequestParam String cpf,
            ModelMap model) {
        System.out.println("Acionado método processarSaldo() - POST");
        System.out.println("Consultando saldo da conta: " + numeroConta);
        
        try {
            ContaCorrente conta = operacaoBancariaService.consultarSaldo(numeroConta, agencia, cpf);
            
            if (conta == null) {
                model.addAttribute("erro", "Conta não encontrada ou dados inválidos.");
                model.addAttribute("exibindo", false);
            } else {
                // Carregar dados da conta
                model.addAttribute("nomeCliente", "Cliente da Conta");
                model.addAttribute("numeroConta", conta.getNumeroConta());
                model.addAttribute("agencia", conta.getAgencia());
                model.addAttribute("saldo", String.format("%.2f", conta.getSaldo()));
                model.addAttribute("exibindo", true);
            }
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao consultar saldo: " + e.getMessage());
            model.addAttribute("exibindo", false);
            System.out.println("Erro ao consultar saldo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "/banco/saldo";
    }

    // =============================
    // ROTAS DO MÓDULO DE REVISÃO
    // =============================

    @RequestMapping(value = "/revisao", method = RequestMethod.GET)
    public String carregarRevisaoRoot() {
        System.out.println("Redirecionando para /revisao/inicio");
        return "redirect:/revisao/inicio";
    }

    @RequestMapping(value = "/revisao/inicio", method = RequestMethod.GET)
    public String carregarRevisaoInicio(ModelMap model) {
        System.out.println("Acionado método carregarRevisaoInicio().");
        model.addAttribute("titulo", "Duke Revisão");
        model.addAttribute("descricao", "Introdução ao módulo Duke Revisão");
        return "revisao/inicio";
    }
    @RequestMapping(value = "/revisao/carrinho", method = RequestMethod.GET)
    public String carregarRevisaoCarrinho(ModelMap model) {
        System.out.println("Acionado método carregarRevisaoCarrinho().");
        model.addAttribute("titulo", "Duke Revisão");
        model.addAttribute("descricao", "Introdução ao módulo Duke Revisão");
        return "revisao/carrinho";
    }
        @RequestMapping(value = "/revisao/pagamento", method = RequestMethod.GET)
    public String carregarRevisaoPagamento(ModelMap model) {
        System.out.println("Acionado método carregarRevisaoPagamento().");
        model.addAttribute("titulo", "Duke Revisão");
        model.addAttribute("descricao", "Introdução ao módulo Duke Revisão");
        return "revisao/pagamento";
    }
    @RequestMapping(value = "/revisao/produtos", method = RequestMethod.GET)
    public String carregarRevisaoProdutos(ModelMap model) {
        System.out.println("Acionado método carregarRevisaoProdutos().");
        model.addAttribute("titulo", "Duke Revisão");
        model.addAttribute("descricao", "Introdução ao módulo Duke Revisão");
        return "revisao/produtos";
    }
}
