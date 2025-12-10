package com.codeboy.mvc.model.dto;

public class ChatRequest {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ChatRequest [message=" + message + "]";
	}

	public ChatRequest(String message) {
		super();
		this.message = message;
	}

	public ChatRequest() {
		super();
	}
	
}
