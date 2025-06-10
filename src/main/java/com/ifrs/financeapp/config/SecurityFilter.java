package com.ifrs.financeapp.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ifrs.financeapp.exceptions.InvalidTokenException;
import com.ifrs.financeapp.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // String path = request.getRequestURI();

        // if (path.equals("/auth/login") || path.equals("/auth/register")) {
        // filterChain.doFilter(request, response);
        // return;
        // }

        try {

            var token = recoverToken(request);

            if (token != null && !token.isBlank()) {
                var subject = tokenService.validateToken(token);

                if (subject != null && !subject.isBlank()) {
                    UserDetails user = userRepository.findByLogin(subject);

                    if (user != null) {
                        var authentication = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (InvalidTokenException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            response.getWriter().flush();
        } catch (Exception e) {
            throw e;
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }

}
