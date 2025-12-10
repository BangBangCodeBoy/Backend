package com.codeboy.mvc.jwt;

import com.codeboy.mvc.model.dto.CustomUserDetails;
import com.codeboy.mvc.model.dto.request.LoginRequest;
import com.codeboy.mvc.model.dto.response.ApiResponse;
import com.codeboy.mvc.model.dto.response.LoginResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper; // ✅ 추가

    {
        // 인스턴스 초기화 블록 or 생성자에서 설정
        setFilterProcessesUrl("/login");  // ✅ 이 URL로 오는 요청을 로그인으로 처리
    }

    /**
     * 로그인 시도 시 호출되는 메서드
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        LoginRequest loginRequest =
                null;
        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String username = loginRequest.getId();
        String password = loginRequest.getPassword();

        if (username == null) {
            username = request.getParameter("username");
        }
        if (password == null) {
            password = request.getParameter("password");
        }



        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

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

        // 1) 인증된 사용자 정보
        CustomUserDetails principal = (CustomUserDetails) authResult.getPrincipal();
        String username = principal.getUsername();
        String role = principal.getAuthorities().iterator().next().getAuthority();

        // 2) JWT 생성
        String accessToken = jwtUtil.createJwt(username, role, 60 * 60 * 1000L);
        String refreshToken = jwtUtil.createJwt(username, role, 7 * 24 * 60 * 60 * 1000L);


        // 3) LoginResponse 생성
        LoginResponse loginResponse = new LoginResponse(
                accessToken,
                refreshToken,
                principal.getMemberId()
        );

        // 4) ApiResponse<LoginResponse> 생성
        ApiResponse<LoginResponse> apiResponse =
                ApiResponse.success(HttpStatus.OK, "로그인 성공", loginResponse);

        // 5) JSON으로 내려주기
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), apiResponse);
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

        ApiResponse<Void> apiResponse =
                ApiResponse.failure(HttpStatus.UNAUTHORIZED,  failed.getMessage()); // or failed.getMessage()

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
