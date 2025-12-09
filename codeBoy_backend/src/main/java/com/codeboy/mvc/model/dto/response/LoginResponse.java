package com.codeboy.mvc.model.dto.response;

// 예: com.codeboy.mvc.model.dto.response 패키지에 둔다
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private Long memberId;
    private String id;
    private String nickname;
}
