package com.codeboy.mvc.model.dao;

import java.util.List;

import com.codeboy.common.ProblemType;
import com.codeboy.mvc.model.dto.Problem;

public interface IncorrectNoteDao {

    //유저의 아이디로 유저가 틀린 문제들의 모음을 조회
    public List<Problem> selectIncorrectProblems(long memberId);

    //유저가 자신의 오답노트 안에서 문제를 삭제
    public void deleteIncorrectProblem(long memberId, long incorrectNoteId);

    //오답노트에 문제 추가
    public void insertIncorrectProblem(long memberId, long problemId, long userProblemId, ProblemType problemType);
}


