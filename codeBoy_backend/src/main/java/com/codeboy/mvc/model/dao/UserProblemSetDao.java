package com.codeboy.mvc.model.dao;

import com.codeboy.mvc.model.dto.Problem;

import java.util.List;

public interface UserProblemSetDao {
    public List<Problem> selectProblemsByUserProblemSetId(long userProblemSetId);



}
