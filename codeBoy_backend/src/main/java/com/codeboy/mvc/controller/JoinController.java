package com.codeboy.mvc.controller;

import com.codeboy.mvc.model.dto.request.JoinRequest;
import com.codeboy.mvc.model.dto.response.ApiResponse;
import com.codeboy.mvc.model.service.JoinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join")
@Tag(name="join RESTful API", description = "회원가입 REST API")
public class JoinController {
    private final JoinService joinService;
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> adminP(@RequestBody JoinRequest joinRequest) {
        try {
            Long memberId = joinService.joinProcess(joinRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success( HttpStatus.CREATED, "회원가입에 성공했습니다.", memberId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }
}