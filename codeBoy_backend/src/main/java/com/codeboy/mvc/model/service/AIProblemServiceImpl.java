package com.codeboy.mvc.model.service;


import com.codeboy.common.Category;
import com.codeboy.mvc.config.GmsClient;
import com.codeboy.mvc.model.dto.AIProblem;
import com.codeboy.mvc.model.dto.request.AIProblemRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIProblemServiceImpl implements AIProblemService {

    private final GmsClient gmsClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AIProblemServiceImpl(GmsClient gmsClient) {
        this.gmsClient = gmsClient;
    }

    @Override
    public List<AIProblem> generateProblems(AIProblemRequest request) {

        String category = request.getCategory();
       
       
        String prompt = """
                너는 %s 과목의 4지선다 객관식 문제를 출제하는 출제자다.
                아래 조건을 반드시 지켜라.

                [조건]
                - 총 1개의 문제를 만든다.
                - 각 문제는 하나의 정답만 가진다.
                - 각 문제마다 보기는 choice1, choice2, choice3, choice4 정확히 4개이다.
                - 정답 인덱스(answer)는 1,2,3,4 중 하나이다.
                - 출력은 오직 JSON 배열 형식으로만 한다. 그 외의 설명, 문장, 마크다운을 절대 쓰지 마라.
                [JSON 형식 예시]
                [
                  {
                    "problemDescription": "문제 내용",
                    "choice1": "첫번째 선지",
                    "choice2": "두번째 선지",
                    "choice3": "세번째 선지",
                    "choice4": "네번째 선지",
                    "answer": 1,
                    "category": %s
                  }
                ]

                이제 %s 과목의 기출/개념을 바탕으로 위 형식의 문제 1개를 생성해라.
                """.formatted(category, category, category);

        String jsonContent = gmsClient.requestQuestionsFromGms(prompt);

        try {
            return objectMapper.readValue(jsonContent, new TypeReference<List<AIProblem>>() {});
        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 실패: " + e.getMessage(), e);
        }
    }
}
