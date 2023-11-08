package com.vhws.karaoke.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    /**
     * Este método irá filtrar as permissões de cada role dos usuarios
     * @param httpSecurity é a intancia da classe que ira fazer a permissão das roles
     * @return Retorna um filtro do que é e do que não é permitido em cada role
     * @throws Exception caso haja algum erro no filtro irá retornar esta exceção
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/general").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/general").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/general").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Este método é responsavel por cuidar da autenticação do usuario
     * @param authenticationConfiguration é a instancia da classe que trabalha com as configurações de autenticação
     * @return Retorna um  Responsavel pela autenticação
     * @throws Exception caso haja algum erro no filtro irá retornar esta exceção
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Este método é responsavel por criptografar as senhas
     * @return Retorna um criptografador
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}