package com.codeboy.mvc.model.dao;

import java.util.List;

import com.codeboy.mvc.model.dto.Comment;

public interface CommentDao {
    public List<Comment> selectAllByProblemId(long problemId);
    
    public void insertComment(Comment comment);

    public void updateComment(Comment comment);

    public void deleteComment(long commentId);
}
