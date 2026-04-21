package faculdade.login;
import model.entidades.Cliente;
import faculdade.login.repository.ClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/*
https://ubiquitous-spork-jpvg6x9x63qxjw-8081.app.github.dev/home


URL Para acessar o H2 - pela décima vez acerte. Não esqueça mais.
H2
H2
H2
    https://ubiquitous-spork-jpvg6x9x63qxjw-8081.app.github.dev/h2-console/login.do?jsessionid=d19584cf586906829d81ebaa5dfd9b6e

    
String para conectar no H2 quando a tela estiver aberta.
    jdbc:h2:mem:test

    usuario - sa
    senha - password

Exemplos HTML
    https://www.w3schools.com/TAgs/tag_nav.asp

 */
import java.util.List;

@SpringBootApplication
@RestController
@ComponentScan
public class ProjetoWEB {
    public static void main(String[] args) throws Throwable {
        System.out.println("Teste ProjetoWEB");
        SpringApplication.run(ProjetoWEB.class, args);
    }
    @Bean
    public CommandLineRunner init(@Autowired ClienteDAO clientes) {
        return args -> {
            Cliente c = new Cliente();
            c.setNome("Douglas Mendes");
            clientes.salvarCliente(c);

            c = new Cliente("Robson Itapoa.");
            clientes.salvarCliente(c);
            List <Cliente> listaCli = clientes.obterTodos();
            listaCli.forEach(System.out::println);;

            listaCli = clientes.obterClienteByNome("Douglas");
            listaCli.forEach(System.out::println);

            System.out.println("Atualizando Clientes");
            listaCli.forEach(cl -> {
                cl.setNome(cl.getNome() + " Atualizado");
                clientes.atualizarCliente(cl);
            });
            System.out.println("Listando Clientes Atualizados");
            listaCli = clientes.obterTodos();
            listaCli.forEach(System.out::println);;

            listaCli = clientes.obterClienteByNome("Doug");
            System.out.println("Listando Cliente especifico");
            listaCli.forEach(System.out::println);;

            System.out.println("Removendo 1 Cliente");
            listaCli.forEach(cl -> {
                cl.setId(1);
                clientes.deletarCliente(cl);
            });
            listaCli = clientes.obterTodos();
            System.out.println("Listando Clientes pela ultima vez");
            listaCli.forEach(System.out::println);

            // Ao final do processo, a tabela de clientes deve estar vazia, pois deletamos o cliente com id 1 e o cliente com id 2 foi atualizado para "Robson Itapoa. Atualizado", mas não foi deletado.
            // Portanto, o cliente "Robson Itapoa. Atualizado" deve permanecer na tabela, enquanto o cliente "Douglas Mendes" foi deletado. 
            // Se a tabela de clientes estiver vazia, isso indica que ambos os clientes foram deletados, o que não é o esperado.
            // Se a tabela de clientes contiver apenas o cliente "Robson Itapoa. Atualizado", isso indica que o cliente "Douglas Mendes" foi deletado corretamente, enquanto o cliente


            // Será acionada a classe de security para autenticar o usuário, e a partir disso, o usuário poderá acessar as rotas protegidas do sistema, como por exemplo, a rota de operações bancárias, onde ele poderá realizar saques, depósitos e transferências. 
            // O processo de autenticação e autorização é fundamental para garantir a segurança do sistema, evitando que usuários não autorizados acessem informações sensíveis ou realizem operações indevidas.
            // a classe de security é a classe responsável por implementar as regras de autenticação e autorização, utilizando o Spring Security para proteger as rotas do sistema.
            // Ela utilizar csrf token para proteger as rotas contra ataques de cross-site request forgery, e também implementa a autenticação baseada em JWT (JSON Web Token) para garantir que apenas usuários autenticados possam acessar as rotas protegidas do sistema.
            // Nome da classe de security: MinhaClasseFilterChain no pacote faculdade.login.security, onde serão implementadas as regras de segurança para o sistema, incluindo a configuração do filtro de autenticação e autorização, e a definição das rotas protegidas do sistema.

        };
    }

}

