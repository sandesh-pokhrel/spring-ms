package com.sandesh.miscservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirstFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String admin = request.getHeader("X-SuperAdmin");
        String user = request.getHeader("X-SuperUser");

        if (StringUtils.isNotBlank(admin)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(admin, null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (StringUtils.isNotBlank(user)) {
            String password = request.getHeader("SuperUser-Password");
            try {
                Authentication auth = authenticationManager.authenticate(new SuperUserAuthentication(user, password));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (AuthenticationCredentialsNotFoundException ex) {
                response.getWriter().write("""
                        {
                          "message": "exception message",
                          "exMessage": "login failed"
                        }
                        """);
                response.setContentType("application/json");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
