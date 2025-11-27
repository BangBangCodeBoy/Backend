package com.codeboy.mvc.controller;

import com.codeboy.mvc.model.responseDto.ApiResponse;
import com.codeboy.mvc.model.responseDto.IncorrectNoteResponse;
import com.codeboy.mvc.model.service.IncorrectNoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/incorrect-note")
public class IncorrectNoteController {
    @Autowired
    private IncorrectNoteServiceImpl incorrectNoteService;

    //한 회원의 오답노트를 모두 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<IncorrectNoteResponse>>> getIncorrectNote(){
        //TODO : 세션에서 memberId 가져오기
        Long memberId = 1L;
        if (memberId == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>( HttpStatus.BAD_REQUEST, "회원 ID를 찾을 수 없습니다.", null));
        }
        List<IncorrectNoteResponse> incorrectNoteList=  incorrectNoteService.getIncorrectNoteList(memberId);

        if (incorrectNoteList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(HttpStatus.NOT_FOUND, "오답노트를 조회하는 데 실패하였습니다.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, "오답노트가 성공적으로 조회되었습니다", incorrectNoteList));
    }
}
