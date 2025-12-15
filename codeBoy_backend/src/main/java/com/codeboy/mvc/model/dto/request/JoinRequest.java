package com.codeboy.mvc.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    private String id;
    private String password;
    private String nickname;
    private String email;
}
