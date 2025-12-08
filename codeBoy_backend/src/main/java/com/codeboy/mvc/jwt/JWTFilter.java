package com.codeboy.mvc.jwt;

import com.codeboy.mvc.model.service.CustomerUserDetailService;
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

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomerUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 헤더에서 Authorization 꺼내기
        String authorization = request.getHeader("Authorization");

        // 2. 토큰이 없거나 Bearer 형식이 아니면 → 그냥 다음 필터로 넘기고 끝
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            // System.out.println("JWTFilter: 토큰 없음");
            filterChain.doFilter(request, response);
            return;
        }

        // 3. "Bearer xxxxxx" 에서 실제 토큰 부분만 분리
        String token = authorization.substring(7); // "Bearer " 길이 = 7

        // 4. 토큰 만료 여부 검사
        if (jwtUtil.isExpired(token)) {
            // System.out.println("JWTFilter: 토큰 만료");
            filterChain.doFilter(request, response);
            return;
        }

        // 5. 토큰에서 username 추출
        String username = jwtUtil.getUsername(token);
        // String role = jwtUtil.getRole(token); // 필요하면 role도 사용 가능

        // 6. DB 또는 UserDetailsService를 통해 유저 정보 로드
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        // 7. Authentication 객체 생성 (credentials는 JWT 기반이라 null로 둬도 됨)
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        // 8. SecurityContext에 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 9. 다음 필터 진행
        filterChain.doFilter(request, response);
    }
}
