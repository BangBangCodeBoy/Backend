package com.codeboy.mvc.config;

import com.codeboy.mvc.jwt.JWTFilter;
import com.codeboy.mvc.jwt.JWTUtil;
import com.codeboy.mvc.jwt.LoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper; // 필요 없으면 지워도 됨
    private final JWTUtil jwtUtil;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 특정 Http 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 기본 제공 로그인/HTTP Basic/CSRF 비활성화
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        // 인가(접근 권한) 설정
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN") // "ADMIB" 오타 수정
                        .anyRequest().authenticated()
                );

        // 세션 사용하지 않는(JWT 등) 방식으로 설정
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // 커스텀 로그인 필터 추가 (UsernamePasswordAuthenticationFilter 위치에 대체)
        AuthenticationManager authenticationManager =
                authenticationConfiguration.getAuthenticationManager();

        http.addFilterAt(
                new LoginFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    // AuthenticationManager를 Bean으로도 노출하고 싶으면 (선택사항)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
