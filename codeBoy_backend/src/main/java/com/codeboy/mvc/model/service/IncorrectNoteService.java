package com.codeboy.mvc.model.service;

import com.codeboy.common.ProblemType;
import com.codeboy.mvc.model.dto.IncorrectNote;
import com.codeboy.mvc.model.dto.Problem;
import com.codeboy.mvc.model.responseDto.IncorrectNoteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IncorrectNoteService {
    //오답노트에 문제 넣기
    public void addIncorrectNote(Long memberId, Long problemId, Long userProblemId, ProblemType problemType);
    //한 유저의 모든 오답노트 조회하기
    public List<IncorrectNoteResponse> getIncorrectNoteList(long memberId);

    //오답노트 삭제하기
    public void deleteIncorrectNote(long incorrectNoteId);



}
