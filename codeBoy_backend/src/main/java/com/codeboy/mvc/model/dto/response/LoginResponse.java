package com.codeboy.mvc.model.dto.response;

// 예: com.codeboy.mvc.model.dto.response 패키지에 둔다
import com.codeboy.mvc.model.dto.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken; // 추가
    private Long memberId;
}
