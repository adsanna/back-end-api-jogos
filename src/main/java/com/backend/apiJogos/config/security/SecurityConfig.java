package com.backend.apiJogos.config.security;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    return http
        .csrf(csrf -> csrf.disable())

        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .authorizeHttpRequests(auth -> auth

            .requestMatchers(
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**")
            .permitAll()

            .requestMatchers(
                HttpMethod.GET,
                "/games/**")
            .permitAll()

            .anyRequest()
            .authenticated())

        .exceptionHandling(ex -> ex

            .authenticationEntryPoint((request, response, authException) -> {

              response.setStatus(401);
              response.setContentType(MediaType.APPLICATION_JSON_VALUE);

              Map<String, Object> body = Map.of(
                  "timestamp", LocalDateTime.now().toString(),
                  "status", 401,
                  "error", "Unauthorized",
                  "message", "Token inválido, expirado ou ausente",
                  "path", request.getRequestURI());

              new ObjectMapper()
                  .writeValue(response.getOutputStream(), body);
            })

            .accessDeniedHandler((request, response, accessDeniedException) -> {

              response.setStatus(403);
              response.setContentType(MediaType.APPLICATION_JSON_VALUE);

              Map<String, Object> body = Map.of(
                  "timestamp", LocalDateTime.now().toString(),
                  "status", 403,
                  "error", "Forbidden",
                  "message", "Você não possui permissão para acessar este recurso",
                  "path", request.getRequestURI());

              new ObjectMapper()
                  .writeValue(response.getOutputStream(), body);
            }))

        .oauth2ResourceServer(oauth -> oauth.jwt())

        .build();
  }
}
