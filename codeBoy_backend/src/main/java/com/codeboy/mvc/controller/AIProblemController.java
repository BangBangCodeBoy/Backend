package com.codeboy.mvc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeboy.mvc.model.dto.AIProblem;
import com.codeboy.mvc.model.dto.request.AIProblemRequest;
import com.codeboy.mvc.model.service.AIProblemService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ai-problems")
@Tag(name = "문제생성 RESTful API", description = "문제생성 CRUD를 할 수 있는 REST API")
public class AIProblemController {

	private final AIProblemService aiProblemService;

	public AIProblemController(AIProblemService aiProblemService) {
		this.aiProblemService = aiProblemService;
	}

	@PostMapping
	public ResponseEntity<List<AIProblem>> generateProblems(@RequestBody AIProblemRequest request) {
		System.out.println("=== /api/ai-problems 호출 ===");
	    System.out.println("request 객체: " + request);
	    if (request != null) {
	    	request.setCategory("정보처리기사");
	    } else {
	        System.out.println("request가 null입니다");
	    }
		return ResponseEntity.ok(aiProblemService.generateProblems(request));
	}
}
