package com.backend.SpringbootBackend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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
}
