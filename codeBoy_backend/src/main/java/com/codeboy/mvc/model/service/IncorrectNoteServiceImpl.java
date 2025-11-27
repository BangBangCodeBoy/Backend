package com.codeboy.mvc.model.service;

import com.codeboy.common.ProblemType;
import com.codeboy.mvc.model.dao.IncorrectNoteDao;
import com.codeboy.mvc.model.dto.Problem;
import com.codeboy.mvc.model.responseDto.IncorrectNoteResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncorrectNoteServiceImpl implements IncorrectNoteService{
    @Autowired
    private IncorrectNoteDao incorrectNoteDao;

    @Override
    public void addIncorrectNote(Long memberId, Long problemId, Long userProblemId, ProblemType problemType) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("problemId", problemId);
        params.put("userProblemId", userProblemId);
        params.put("problemType", problemType);

        incorrectNoteDao.insertIncorrectProblem(params);
    }

    @Override
    public List<IncorrectNoteResponse> getIncorrectNoteList(long memberId) {
        return incorrectNoteDao.selectIncorrectProblems(memberId);
    }

    @Override
    public void deleteIncorrectNote(long incorrectNoteId) {
        incorrectNoteDao.deleteIncorrectProblem(incorrectNoteId);
    }
}
