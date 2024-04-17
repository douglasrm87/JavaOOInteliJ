package faculdade.login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Controller
public class MinhasRotas implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }
    //mapeando para que a requisição cai neste metodo quando for acessar pelo browser com Verbos Rest
    // Testar com - http://localhost:8080/casa
    @RequestMapping(value="/casa", method = RequestMethod.GET)
    public String carregarHello(){
        return "hello";
    }
}
