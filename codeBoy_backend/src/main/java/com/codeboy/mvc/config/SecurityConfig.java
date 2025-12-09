package com.codeboy.mvc.config;

import com.codeboy.mvc.jwt.JWTFilter;
import com.codeboy.mvc.jwt.JWTUtil;
import com.codeboy.mvc.jwt.LoginFilter;
import com.codeboy.mvc.model.service.CustomerUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomerUserDetailService customerUserDetailService;
    private final JWTUtil jwtUtil;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper; //


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        var builder = http.getSharedObject(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder.class);

        builder
                .userDetailsService(customerUserDetailService)
                .passwordEncoder(bCryptPasswordEncoder());

        return builder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationManager authenticationManager) throws Exception {

        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/join",      "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/swagger-resources/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ✅ 보통 이렇게 씀
                        .anyRequest().authenticated()
                );

        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterAt(
                new LoginFilter(authenticationManager, jwtUtil, objectMapper),
                UsernamePasswordAuthenticationFilter.class
        );

        http.addFilterBefore(
                new JWTFilter(jwtUtil, customerUserDetailService),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
