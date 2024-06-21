package scari.corp.infoinvest.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProder(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/reg", "/", "error", "/css/**").permitAll() //вход без авторизации
                    .requestMatchers("/admin/**").hasAuthority("admin") // доступ только для admin
                    .requestMatchers("/user/**").hasAuthority("user") // доступ только для user
                    .requestMatchers("/**").authenticated()) //с авторизацией и аутентификацией
                .formLogin(form -> form 
                    .loginPage("/login")    // указываем путь к форме входа
                    // .loginProcessingUrl("/login")   // URL для обработки отправки формы
                    .permitAll())           
                .logout(logout -> logout
                    .logoutUrl("/logout") // URL, который будет вызывать процесс выхода
                    .logoutSuccessUrl("/") // URL, на который будет перенаправлен пользователь после успешного выхода
                    .permitAll())
                .build();
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


