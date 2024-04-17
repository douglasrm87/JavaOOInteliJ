package org.example;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication {
    @GetMapping("/alomundo")
    public String AloMundoWEB(){
        return "<h1>Meu Alo Mundo em Spring Boot.</h1>";
    }
    public static void main(String[] args) {
        SpringApplication.run (VendasApplication.class,args);
        System.out.println("Classe Vendas Application");
    }
}
