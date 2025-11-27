package com.codeboy.mvc.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.codeboy.mvc.model.dto.Comment;

@Mapper
public interface CommentDao {
    public List<Comment> selectCommentsByuserProblemSetId(long userProblemSetId);

    //유저문제세트 id에다가 댓글을 달아야하므로 long userProblemSetId으로 지정
    public int insertComment(long userProblemSetId, Comment comment);

    //조회된 댓글의 id를 가져와서 수정하고 삭제함.
    public int updateComment(long commentId, Comment comment);

    //조회된 댓글의 id를 가져와서 수정하고 삭제함.
    public int deleteComment(long commentId);
    
    //댓글아이디로 댓글 작성자의 아이디를 가져옴
    public Long selectCommentOwnerId(long commentId);

}
