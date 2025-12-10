package com.codeboy.mvc.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeboy.mvc.model.dto.ChatRequest;
import com.codeboy.mvc.model.dto.ChatResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("/api/chat")
public class ChatController {
	
	//chatClient 생성 주입
	private final ChatClient chatClient;
	public ChatController(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}
	
	@PostMapping
	public  ChatResponse chat(@RequestBody ChatRequest chatRequest) {
		//기본 챗 클라이언트로 요청을 보내고 응답을 받아본다. 
		String answer = chatClient
		.prompt()
		.system("Answer in Korean")
		.user(chatRequest.getMessage())
		.call() //요청을 ai에게 보냄!
		.content() //응답을 스트링으로 받기 
		return ChatResponse(answer);
	}
	
}
