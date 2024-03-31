package com.sandesh.miscservice.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SuperUserAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = User.builder()
                .username("user")
                .password("pass")
                .roles("ADMIN")
                .build();
        if ((authentication.getPrincipal()).equals(user.getUsername()) && authentication.getCredentials().equals(user.getPassword())) {
            return new SuperUserAuthentication(user.getUsername(), user.getPassword());
        }
        throw new AuthenticationCredentialsNotFoundException("Credentials not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(SuperUserAuthentication.class);
    }
}
