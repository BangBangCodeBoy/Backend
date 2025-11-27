package com.codeboy.mvc.model.dao;

import java.util.List;
import java.util.Map;

import com.codeboy.common.ProblemType;
import com.codeboy.mvc.model.dto.Problem;
import com.codeboy.mvc.model.responseDto.IncorrectNoteResponse;

public interface IncorrectNoteDao {

    //유저의 아이디로 유저가 틀린 문제들의 모음을 조회
    //sql의 관점에서
    //
    public List<IncorrectNoteResponse> selectIncorrectProblems(long memberId);

    //유저가 자신의 오답노트 안에서 문제를 삭제
    public void deleteIncorrectProblem( long incorrectNoteId);

    //오답노트에 문제 추가
    public void insertIncorrectProblem(Map<String, Object> params);
}


