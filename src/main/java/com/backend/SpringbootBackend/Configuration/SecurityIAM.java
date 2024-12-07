package com.backend.SpringbootBackend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityIAM {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){

        UserDetails savindu = User.builder()
                .username("Savindu")
                .password("{noop}root")
                .roles("ADMIN") 
                .build();

        return new InMemoryUserDetailsManager(savindu);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(HttpMethod.GET, "api/students/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "api/students/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "api/students/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "api/students/**").hasRole("ADMIN")
        );
        return http.build();
    }
}
