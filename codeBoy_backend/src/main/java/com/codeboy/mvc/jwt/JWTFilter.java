package com.codeboy.mvc.jwt;

import com.codeboy.mvc.model.service.CustomerUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomerUserDetailService userDetailService;

    // ❗ 실제 사용하는 URL에 맞게 수정해야 함
    private static final List<String> EXCLUDED_URLS = List.of(
            "/login",// 스프링 시큐리티 로그인 엔드포인트
            "/api/join",
            "/member/login",   // 커스텀 로그인 엔드포인트가 있다면
            "/auth/login",
            "/auth/signup",
            "/member/join"
    );

    private boolean isExcluded(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDED_URLS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 0. 로그인/회원가입 등 공개 URL이면 토큰 검사 없이 그냥 패스
        if (isExcluded(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. Authorization 헤더 가져오기
        String authorization = request.getHeader("Authorization");

        // 2. 토큰이 없거나 Bearer 형식이 아니면 → 그냥 다음 필터로
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("JWTFilter: 토큰 없음, 다음 필터로 패스");
            filterChain.doFilter(request, response);
            return;
        }

        // 3. "Bearer xxxxxx" 에서 실제 토큰 부분만 분리
        String token = authorization.substring(7); // "Bearer " 길이 = 7

        try {
            // 4. 토큰 만료 여부 체크 (이 과정에서 ExpiredJwtException이 날 수 있음)
            if (jwtUtil.isExpired(token)) {
                throw new ExpiredJwtException(null, null, "Token expired");
            }

            // 5. 여기까지 왔으면 만료 안 된 토큰이므로 username 파싱
            String username = jwtUtil.getUsername(token);

            // 6. UserDetails 조회
            UserDetails userDetails = userDetailService.loadUserByUsername(username);

            // 7. Authentication 객체 생성해서 SecurityContext에 넣기
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // 8. 다음 필터 진행
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            // 9. 만료된 토큰일 때 401로 응답
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("""
                {"code": "TOKEN_EXPIRED", "message": "토큰이 만료되었습니다. 다시 로그인 해주세요."}
            """);
        }
    }
}
