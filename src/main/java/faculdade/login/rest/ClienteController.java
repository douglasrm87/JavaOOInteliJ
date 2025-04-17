package faculdade.login.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//https://ubiquitous-spork-jpvg6x9x63qxjw-8081.app.github.dev/api/clientes/douglas
@Controller
public class ClienteController {
    // Métodos que virão a partir da URL
    @RequestMapping(value = {"/api/clientes/{nome}"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public String aloClientes(@PathVariable("nome") String nomeCliente) {
        return String.format("<h1> Bom dia Sr(a). %s </h1>", nomeCliente);
    }
}
