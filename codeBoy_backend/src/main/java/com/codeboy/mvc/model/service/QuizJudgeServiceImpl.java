package com.codeboy.mvc.model.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class QuizJudgeServiceImpl {

    private final ChatClient chatClient; // GPT-4.1 or Gemini Pro
    
    
    public String judge(String questionsJson) {
        return chatClient.prompt().user("""
               다음 문제 세트가 아래 기준에 적합한지 검증하라.
               - 사실성(Fact Correct)
               - 난이도 적절
               - 오답 옵션 근거 있음
               - JSON 형식 유효

               반드시 JSON 형식으로:
               { "valid": true, "comments": "..." }

               """ + questionsJson).call().content();
    }
}
