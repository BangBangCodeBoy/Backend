package com.codeboy.mvc.model.service;

import com.codeboy.common.Category;
import com.codeboy.mvc.model.dao.ProblemDao;
import com.codeboy.mvc.model.dto.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService{
    @Autowired
    private ProblemDao problemDao;

    @Override
    public List<Problem> getProblems(int limit, Category category) {
        return problemDao.selectProblem(limit,category);
    }
}
