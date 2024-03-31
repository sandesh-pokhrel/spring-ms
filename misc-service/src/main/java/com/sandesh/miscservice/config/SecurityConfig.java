package com.sandesh.miscservice.config;

import com.sandesh.miscservice.security.FirstFilter;
import com.sandesh.miscservice.security.SuperUserAuthenticationProvider;
import com.sandesh.miscservice.security.UsernamePasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final SuperUserAuthenticationProvider superUserAuthenticationProvider;


    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http, FirstFilter firstFilter) throws Exception {
        return http
                .securityMatcher("/api/v1/**")
                .authorizeHttpRequests(reg -> reg.anyRequest().authenticated())
                .csrf(cus -> cus.ignoringRequestMatchers("/**"))
                .cors(cus -> cus.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedMethods(List.of("*"));
                    return corsConfiguration;
                }))
                .addFilterBefore(firstFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

//    @Bean
//    @Order(4)
    public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v2/**")
                .authorizeHttpRequests(reg -> reg.anyRequest().authenticated())
                .csrf(cus -> cus.ignoringRequestMatchers("/**"))
                .cors(cus -> cus.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedMethods(List.of("*"));
                    return corsConfiguration;
                }))
                .userDetailsService(userDetailsService())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    //@Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.builder()
                .username("user")
                .password("pass")
                .roles("ADMIN")
                .build());
        return inMemoryUserDetailsManager;
    }

    @Bean(name = "customAuthManager")
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(usernamePasswordAuthenticationProvider, superUserAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
