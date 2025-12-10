package com.codeboy.mvc.model.dto;

import java.sql.Timestamp;
import java.util.List;

import com.codeboy.common.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AIProblem {

    private String problemDescription;// 문제 본문  
    private String choice1;		   // 보기 4개   
    private String choice2;
    private String choice3;
    private String choice4;
    private int answer;            // 정답 인덱스 (0~3)	
    private String category;
}
