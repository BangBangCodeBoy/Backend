package com.codeboy.mvc.model.dao;

import java.util.List;

import com.codeboy.common.Category;
import com.codeboy.mvc.model.dto.UserProblem;

public interface UserProblemDao extends ProblemDao{

	//문제세트 조회
	public List<UserProblem> selectUserProblem(Category category);
	//문제 생성하기
	public void insertUserProblem(UserProblem userProblem);

	//문제 세트 만들기
	public void insertUserProblemSet(List<UserProblem> userProblemList);

	//문제 수정
	public void updateUserProblem(UserProblem userProblem);

	//문제 삭제
	public int deleteUserProblem(int id);
}

