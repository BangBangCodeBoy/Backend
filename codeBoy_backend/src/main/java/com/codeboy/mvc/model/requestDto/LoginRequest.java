package com.codeboy.mvc.model.dto;

public class LoginRequest {
	//로그인을 할 때에는 id와 password만 사용하니 이를 간편하게 전달하기 위한 DTO
    private String id;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
