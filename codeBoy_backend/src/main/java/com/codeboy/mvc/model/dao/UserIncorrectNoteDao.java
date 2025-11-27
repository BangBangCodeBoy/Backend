package com.codeboy.mvc.model.dao;

import java.util.List;

import com.codeboy.mvc.model.dto.UserProblem;

public interface UserIncorrectNoteDao {

    //유저의 아이디로 유저가 틀린 문제들의 모음을 조회
    public List<UserProblem> selectUserIncorrectProblems(long memberId);

    //유저가 자신의 오답노트 안에서 문제를 삭제
    public void deleteUserIncorrectProblem(long memberId, long userProblemId);

    //(중간자 테이블)오답노트에 문제 추가
    public void insertUserIncorrectProblem(long memberId, long userProblemId);
}
