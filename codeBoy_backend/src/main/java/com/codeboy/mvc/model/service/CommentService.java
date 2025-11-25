package com.codeboy.mvc.model.service;

import java.util.List;

import com.codeboy.mvc.model.dto.Comment;

public interface CommentService {
    //유저제작 문제 세트Id로 조회
    public List<Comment> getAllCommentsById(long userProblemSetId);
    
    //유저제작 문제 세트Id로 조회후 해당 세트에 댓글 작성
    public int addComment(long userProblemSetId, Comment comment);
//
    public int updateComment(long commentId, Comment comment);
//
    public int deleteComment(long commentId);
    
    
}
