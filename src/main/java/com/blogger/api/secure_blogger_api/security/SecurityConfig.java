package com.blogger.api.secure_blogger_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF because we will use JWT (stateless)
            .csrf(csrf -> csrf.disable())

            // Tell Spring Security not to create sessions
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Define our authorization rules
            .authorizeHttpRequests(auth -> auth
                // RULE #1: Allow anyone to access the homepage and its assets
                .requestMatchers("/", "/index.html", "/style.css", "/script.js", "/assets/**").permitAll()
                
                // RULE #2: Allow anyone to access our future login/register API endpoints
                .requestMatchers("/api/auth/**").permitAll() 
                
                // RULE #3: Any other request must be from a logged-in user
                .anyRequest().authenticated() 
            );

        return http.build();
    }
}