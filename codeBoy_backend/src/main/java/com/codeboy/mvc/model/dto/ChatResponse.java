package com.codeboy.mvc.model.dto;

public class ChatResponse {
	private String reply;

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public ChatResponse(String reply) {
		super();
		this.reply = reply;
	}

	public ChatResponse() {
		super();
	}

	@Override
	public String toString() {
		return "ChatResponse [reply=" + reply + "]";
	}
	
}
