package com.vhws.karaoke.infra;


import java.io.IOException;
import java.util.function.Consumer;

import com.vhws.karaoke.entity.ecxeption.ResourceInternalServerException;
import com.vhws.karaoke.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    /**
     * Este método faz a verificação do token, para saber se ele é ou não é válido
     *
     *
     * */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            var token = this.recoverToken(request);
            if (token != null) {
                var login = tokenService.validateToken(token);
                UserDetails user = userRepository.findByLogin(login);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {

            handleException(response, e);
        }
    }


    private void handleException(HttpServletResponse response, Exception e) {
        Consumer<HttpServletResponse> errorSender = httpResponse -> {
            try {
                httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocorreu um erro no servidor");
            } catch (IOException ex) {

                ex.printStackTrace();
            }
        };

        if (e instanceof ResourceInternalServerException) {
            errorSender.accept(response);
        } else {

            errorSender.accept(response);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}