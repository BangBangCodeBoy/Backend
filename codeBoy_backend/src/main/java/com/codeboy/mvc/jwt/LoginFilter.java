package com.codeboy.mvc.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * 로그인 시도 시 호출되는 메서드
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        // 기본적으로 UsernamePasswordAuthenticationFilter가 제공하는 메서드 사용
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // null일 수도 있으니 한 번 더 방어적으로 처리해도 됨
        if (username == null) {
            username = request.getParameter("username");
        }
        if (password == null) {
            password = request.getParameter("password");
        }

        // 인증 객체 생성 (권한 컬렉션은 null 또는 빈 리스트로 전달)
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        // AuthenticationManager에게 실제 인증 위임
        return authenticationManager.authenticate(authRequest);
    }

    /**
     * 로그인 성공 시 호출되는 메서드
     * (JWT 발급 같은 작업을 여기서 하면 됨)
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {

        System.out.println("login success: " + authResult.getName());

        // JWT 쓰고 싶으면 여기에서 토큰 만들어서 헤더에 담아주면 됨
        // response.addHeader("Authorization", "Bearer " + token);

        // 기본 흐름 계속 진행
        // (필요에 따라 chain.doFilter 호출 여부 선택)
        chain.doFilter(request, response);
    }

    /**
     * 로그인 실패 시 호출되는 메서드
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {

        System.out.println("login fail: " + failed.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
