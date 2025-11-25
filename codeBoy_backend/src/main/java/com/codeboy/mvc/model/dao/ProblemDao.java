package com.codeboy.mvc.model.dao;

import java.util.List;

import com.codeboy.common.Category;
import com.codeboy.mvc.model.dto.Problem;

public interface ProblemDao {
	//문제 조회
	public List<Problem> selectProblem(Category category);

}