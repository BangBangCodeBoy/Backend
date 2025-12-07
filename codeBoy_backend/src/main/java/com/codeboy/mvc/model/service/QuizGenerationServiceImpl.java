package com.codeboy.mvc.model.service;

import org.springframework.ai.chat.client.ChatClient;

public class QuizGenerationServiceImpl {
	private final ChatClient chatClient;

	public QuizGenerationServiceImpl(ChatClient chatClient) {
	        this.chatClient = chatClient;
	    }

	public String generate(String pdfText, int count) {

		String prompt = """
				너는 정보처리기사 출제 전문가다.
				아래 PDF 텍스트 내용을 기반으로 4지선다 객관식 문제 %d개를 생성하라.
				반드시 아래 JSON 형식으로만 응답하라.

				{
				  "category": "정보처리기사",
				  "questions": [
				    {
				      "id": 1,
				      "question": "",
				      "options": ["", "", "", ""],
				      "answerIndex": 0,
				      "explanation": ""
				    }
				  ]
				}

				[PDF TEXT]
				%s
				""".formatted(count, pdfText);

		return chatClient.prompt().user(prompt).call().content();
	}
}
