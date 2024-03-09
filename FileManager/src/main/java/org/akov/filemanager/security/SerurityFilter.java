package org.akov.filemanager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.akov.filemanager.exception.WsException;
import org.akov.filemanager.manager.JwtTokenManager;
import org.akov.filemanager.model.User;
import org.akov.filemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SerurityFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login") || request.getServletPath().equals("/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!request.getServletPath().startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // gérer seulement les request qui commence par /api/

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = token.substring(7);
        int idUser;
        try {
            idUser = JwtTokenManager.getIdUser(jwt);
        } catch (Exception e) {
            throw new WsException(HttpStatus.UNAUTHORIZED, "Token invalide");
        }

        UserDetails user = userService.loadUserByUsername(String.valueOf(idUser));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

}
