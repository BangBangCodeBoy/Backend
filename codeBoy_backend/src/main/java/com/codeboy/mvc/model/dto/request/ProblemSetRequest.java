package com.codeboy.mvc.model.dto.request;

import com.codeboy.common.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description="유저 문제 세트 등록 DTO")
public class ProblemSetRequest {
    private String problemSetTitle;
    private Timestamp createdAt;
    private Category category;
}
