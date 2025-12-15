package com.codeboy.mvc.model.dto;

import com.codeboy.common.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Schema(description = "유저제작문제세트 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserProblemSet {
	private Long userProblemSetId;
    private String problemSetTitle;
    private Timestamp createdAt;
	private Long memberId;
    private Category category;
    private int commentCount;
}
