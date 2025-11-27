package com.codeboy.mvc.controller;

import com.codeboy.common.ProblemType;
import com.codeboy.mvc.model.requestDto.IncorrectNoteRequest;
import com.codeboy.mvc.model.responseDto.ApiResponse;
import com.codeboy.mvc.model.responseDto.IncorrectNoteResponse;
import com.codeboy.mvc.model.service.IncorrectNoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //오답노트에 문제 넣기
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> addIncorrectNote(@RequestBody IncorrectNoteRequest incorrectNoteRequest){
        //TODO : 세션에서 멤버 id 가져오기
        Long memberId = 1L;
        Long problemId = incorrectNoteRequest.getProblemId();
        Long userProblemId = incorrectNoteRequest.getUserProblemId();
        ProblemType problemType  = incorrectNoteRequest.getProblemType();

        if (memberId == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(HttpStatus.BAD_REQUEST, "멤버 ID가 유효하지 않습니다.", null));
        }
        if (problemType == ProblemType.PROBLEM) {
            if (!( problemId != null && userProblemId == null)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST, "문제 ID가 유효하지 않습니다.", null));
            }

        }
        if (problemType == ProblemType.USER_PROBLEM) {
            if (!( problemId == null && userProblemId != null)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST, "유저 문제 ID가 유효하지 않습니다.", null));
            }
        }
//        else if (problemType == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(HttpStatus.BAD_REQUEST, "ProblemType이 유효하지 않습니다.", null));
//        }
        Long incorrectNoteId = incorrectNoteService.addIncorrectNote(memberId,problemId,userProblemId,problemType);

        if (incorrectNoteId == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(HttpStatus.NOT_FOUND, "오답노트 생성에 실패하였습니다.", null));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED, "오답노트를 성공적으로 생성하였습니다.",incorrectNoteId ));
    }

    @DeleteMapping("/{incorrectNoteId}")
    public ResponseEntity<ApiResponse<String>> deleteIncorrectNote(@PathVariable long incorrectNoteId){

        boolean isDeleted = incorrectNoteService.deleteIncorrectNote(incorrectNoteId);
    if (!isDeleted){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(HttpStatus.NOT_FOUND, "유효하지 않은 오답노트입니다.", null));
    }
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "오답노트가 삭제되었습니다", null));
    }
}
