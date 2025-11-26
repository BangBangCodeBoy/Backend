package com.codeboy.mvc.controller;

import com.codeboy.common.Category;
import com.codeboy.mvc.model.responseDto.ApiResponse;
import com.codeboy.mvc.model.requestDto.GetProblemsRequest;
import com.codeboy.mvc.model.dto.Problem;
import com.codeboy.mvc.model.service.ProblemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/problem")
public class ProblemController {

    @Autowired
    private ProblemServiceImpl problemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Problem>>> getProblems(@RequestBody GetProblemsRequest request ) {
        int limit = request.getLimit();
        Category category = request.getCategory();

        if (limit <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "유효하지 않은 limit 입니다", null
            ));
        }
        if (category == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "유효하지 않은 category 입니다.",null
            ));
        }

        List<Problem> problems = problemService.getProblems(limit, category);

        //만약 요청 수보다 존재하는 문제 수가 적다면
        if (limit > problems.size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "요청보다 존재하는 문제 수가 적습니다.", null));
        }

        if (problems.isEmpty()) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "해당되는 문제가 존재하지 않습니다. ", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "문제가 성공적으로 반환되었습니다.", problems));
    }
}
