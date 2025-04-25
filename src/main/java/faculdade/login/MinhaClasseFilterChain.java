package faculdade.login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//https://spring.io/guides/gs/securing-web/
@Configuration
@EnableWebSecurity
public class MinhaClasseFilterChain {
        @Autowired
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions().disable())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                        .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/home"); // Redireciona para uma URL relativa
                        })
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }
    
        @Bean
        public UserDetailsService userDetailsService() {
                UserDetails user = User.builder()
                                .username("drm")
                                .password("{noop}12345") // {noop} indicates no password encoding
                                .roles("USER")
                                .build();
                return new InMemoryUserDetailsManager(user);
        }
 
}

