package com.codeboy.mvc.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeboy.mvc.model.dto.Member;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-member")
@Tag(name="Member RESTful API", description = "Member CRUD를 할 수 있는 REST API")
public class MemberController {
	
//	@Autowired
//	private MemberService memberService;
	
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute Member member){
		return "회원가입 성공?";
	}

	@GetMapping(){
		
	}
	
	

}
