package org.budzet.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index.html", "/style.css", "/img.png", "/money.png").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/") // <-- TA LINIA: po wylogowaniu wraca na stronę główną
                .invalidateHttpSession(true) // Czyści sesję
                .deleteCookies("JSESSIONID") // Kasuje ciasteczka logowania
                .permitAll();

    }
}