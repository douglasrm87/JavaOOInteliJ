package faculdade.login;

import faculdade.login.repository.ClienteDAO;
import model.entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Controller
public class MinhasRotas implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("login");
        registry.addViewController("/cadastro").setViewName("cadastro");
    }

    //mapeando para que a requisição cai neste metodo quando for acessar pelo browser com Verbos Rest
    // Testar com - http://localhost:8080/casa
    @RequestMapping(value = "/hello ", method = RequestMethod.GET)
    public String carregarHello(ModelMap model) {
        System.out.println("Acionado método carregarHello().");
        model.addAttribute("nomeCasa", "Tela Hello carregada.");
        return "hello";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String carregarLogin(ModelMap model) {
        System.out.println("Acionado método carregarLogin().");
        //model.addAttribute("nomeCasa", "Login Executado.");
        return "login";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String carregarLogout(ModelMap model) {
        System.out.println("Acionado método carregarLogout().");
        model.addAttribute("nomeCasa", "Sair do sistema.");
        return "logout";
    }
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String carregarHome(ModelMap model) {
        System.out.println("Acionado método carregarHome().");
        model.addAttribute("nomeCasa", "Carregar a tela de Home.");
        return "home";
    }
    // 1 - Criar a página
    // 2 - Criar o metodo da rota.
    // 3 - Criar o método que vai receber os dados do formulário
    // 4 - Criar o método que vai salvar os dados no banco de dados
    @RequestMapping(value = "/pedido", method = RequestMethod.GET)
    public String criarPedido(ModelMap model) {
        System.out.println("Acionado método criarPedido().");
        model.addAttribute("nomeCasa", "Carregar a tela de Home.");
        return "pedido";
    }
    // 1 - Criar a página
    // 2 - Criar o metodo da rota.
    // 3 - Incluir no home.html.


    // https://vitormoschetti.medium.com/primeiro-crud-com-spring-boot-5b7abd118ded
    @Autowired
    private ClienteDAO clienteRepository;

    @RequestMapping(value = "/cadastro", method = RequestMethod.GET)
    public String salvarClienteController(@ModelAttribute Cliente cli, Model padrao) {
        System.out.println("Acionado método salvarClienteController().");
        padrao.addAttribute("cliente", new Cliente("Digitar algo aqui"));
        System.out.println("Acionado método salvarClienteController().");
        System.out.println("Nome lido no HTML: ");
        System.out.println("Nome digitado no html:" + cli.getNome());
        clienteRepository.salvarCliente(cli);
        return "cadastro";
    }

    @RequestMapping(value = "/consulta", method = RequestMethod.GET)
    public String listarClienteController(@ModelAttribute Cliente cli, Model padrao) {
        System.out.println("Acionado método listarClienteController().");
        padrao.addAttribute("cliente", new Cliente());
        System.out.println("Acionado método listarClienteController().");

            System.out.println("Nome digitado: " + cli.getNome());
            List<Cliente> cliS = clienteRepository.obterClienteByNome(cli.getNome());
            if (cliS != null && cliS.size() > 0 && cliS.get(0).getNome().length() > 0) {
                System.out.println("Dados retornados.");
                padrao.addAttribute("cliente", cliS.get(0));
                System.out.println("Dados selecionados: " + cliS.get(0).getId() + " - " + cliS.get(0).getNome());
            } else {
                padrao.addAttribute("cliente", new Cliente());
            }
        return "consulta";
    }


    @RequestMapping(value = "/banco/frentecaixaeletronico", method = RequestMethod.GET)
    public String operarcaixaEletronico(ModelMap model) {
        System.out.println("Acionado método operarcaixaEletronico().");
        model.addAttribute("nomeCasa", "Carregar a tela de operarcaixaEletronico.");
        return "/banco/frentecaixaeletronico";
    }
    
    @RequestMapping(value = "/banco/cadastrocontacorrente", method = RequestMethod.GET)
    public String cadastrarCaixaEletronico(ModelMap model) {
        System.out.println("Acionado método cadastrarCaixaEletronico().");
        return "/banco/cadastrocontacorrente";
    }

    @RequestMapping(value = "/banco/saquefrentecaixaeletronico", method = RequestMethod.GET)
    public String sacarCaixaEletronico(ModelMap model) {
        System.out.println("Acionado método sacarCaixaEletronico().");
        return "/banco/saquefrentecaixaeletronico";
    }

}
