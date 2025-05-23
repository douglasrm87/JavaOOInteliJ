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



        };
    }

}

