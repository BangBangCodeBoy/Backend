package com.codeboy.mvc.model.service;

public interface CommentService {
    //유저제작 문제 세트Id로 조회
    public List<Comment> getAllCommentsById(int userProblemSetId);

    //
}
