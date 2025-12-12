package com.codeboy.mvc.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class GmsClient {

	private final RestClient restClient;
	private final String gmsKey = "S14P02AQ03-3e0d6365-ebd4-405d-aff1-bd1baacbdd3a";
	private final ObjectMapper objectMapper = new ObjectMapper();

	public GmsClient(RestClient.Builder builder) {
		this.restClient = builder.baseUrl("https://gms.ssafy.io/gmsapi").build();

	}

	/**
	 * GMS에 chat completion 요청을 보내고 choices[0].message.content 문자열만 꺼내서 반환.
	 */
	public String requestQuestionsFromGms(String prompt) {

		Map<String, Object> body = Map.of("model", "gemini-2.0-flash-lite", "contents",
				List.of(Map.of("parts", List.of(Map.of("text", prompt) // 핵심: prompt가 이 위치에 들어감
				))));

		String raw = restClient.post().uri(
				"/generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-lite:generateContent?key=" + gmsKey)
				.body(body).retrieve().body(String.class);
		try {
			// 1) 응답 문자열(raw)을 Jackson ObjectMapper로 JSON 트리 구조로 파싱
			JsonNode root = objectMapper.readTree(raw);
			
			
			// 2) JSON 구조 안에서 우리가 필요한 텍스트만 꺼낸다.
			// root -> "candidates"[0] -> "content" -> "parts"[0] -> "text"
			// 즉, 첫 번째 후보의 첫 번째 part의 text 필드를 꺼내서 String으로 반환
			String result = root.path("candidates").get(0)
					.path("content")
					.path("parts").get(0) 
					.path("text") // 그 안의 text 필드
					.asText(); // String으로 꺼내기
			String result2 = result
				    .replace("```json", "")
				    .replace("```", "")
				    .trim();

			return result2;
		
		} catch (Exception e) {
			// JSON 파싱 중 오류가 나면 런타임 예외로 감싸서 던짐
			// (어디서 문제 터졌는지 메시지 남기기)
			throw new RuntimeException("Gemini 응답 파싱 실패: " + e.getMessage(), e);
		}
	}
}
