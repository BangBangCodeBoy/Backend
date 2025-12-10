package com.codeboy.mvc.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AIProblemRequest {
    private String category;   // 정보처리기사 or SQLD
}
