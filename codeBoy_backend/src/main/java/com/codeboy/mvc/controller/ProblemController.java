
package com.codeboy.mvc.controller;

import com.codeboy.common.Category;
import com.codeboy.mvc.model.dto.response.ApiResponse;
import com.codeboy.mvc.model.dto.Problem;
import com.codeboy.mvc.model.service.ProblemServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problem")
@Tag(name="Problem RESTful API", description = "Problem CRUD를 할 수 있는 REST API")
public class ProblemController {

    @Autowired
    private ProblemServiceImpl problemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Problem>>> getProblems(@RequestParam int limit, @RequestParam Category category) {
        try {
        List<Problem> problems = problemService.getProblems(limit, category);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "문제가 성공적으로 반환되었습니다.", problems));

        }  catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST , e.getMessage()));
        }
    }
}
