package com.codeboy.mvc.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIProblemDto {

    private String problemDescription;        // 문제 본문
    private List<String> choices;  // 보기 4개
    private int answer;       // 정답 인덱스 (0~3)
}
