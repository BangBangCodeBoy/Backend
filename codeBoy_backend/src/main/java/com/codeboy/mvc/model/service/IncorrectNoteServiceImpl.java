package com.codeboy.mvc.model.service;

import com.codeboy.common.ProblemType;
import com.codeboy.mvc.model.dao.IncorrectNoteDao;
import com.codeboy.mvc.model.dto.Problem;
import com.codeboy.mvc.model.responseDto.IncorrectNoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IncorrectNoteServiceImpl{
    @Autowired
    private IncorrectNoteDao incorrectNoteDao;

    public Long addIncorrectNote(Long memberId, Long problemId, Long userProblemId, ProblemType problemType) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("problemId", problemId);
        params.put("userProblemId", userProblemId);
        params.put("problemType", problemType);

        return incorrectNoteDao.insertIncorrectProblem(params);
    }

    public List<IncorrectNoteResponse> getIncorrectNoteList(long memberId) {
        return incorrectNoteDao.selectIncorrectProblems(memberId);
    }

    public boolean deleteIncorrectNote(long incorrectNoteId) {
        int deleted = incorrectNoteDao.deleteIncorrectProblem(incorrectNoteId);
        return deleted > 0;
    }
}
