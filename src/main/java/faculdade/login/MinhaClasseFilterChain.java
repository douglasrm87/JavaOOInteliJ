package faculdade.login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
                        .defaultSuccessUrl("/home", true) // Redirect to /home on successful login
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
/*

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
                                                .defaultSuccessUrl("/home", true) // Redirect to /home on successful login
                                                .permitAll()
                                )
                                .logout((logout) -> logout.permitAll());
                return http.build();
        }

        
public UserDetailsService users() {
	UserDetails user = User.builder()
		.username("user")
		.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
		.roles("USER")
		.build();
	UserDetails admin = User.builder()
		.username("admin")
		.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
		.roles("USER", "ADMIN")
		.build();
	return new InMemoryUserDetailsManager(user, admin);
}

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("drm")
                        .password("12345")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }
         */
}

