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

            c = new Cliente("Juliane Itapoa.");
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

