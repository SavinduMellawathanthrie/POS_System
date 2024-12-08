package com.backend.SpringbootBackend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
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
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/students/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/students/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/students/**").hasRole("ADMIN")
                )
                .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic authentication

        return http.build();
    }




}
