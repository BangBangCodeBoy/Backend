package com.codeboy.mvc.controller;


import com.codeboy.mvc.model.dto.CustomUserDetails;
import com.codeboy.mvc.model.dto.Member;
import com.codeboy.mvc.model.dto.request.DuplicateCheckRequest;
import com.codeboy.mvc.model.dto.request.LoginRequest;
import com.codeboy.mvc.model.dto.request.MemberUpdateRequest;
import com.codeboy.mvc.model.dto.response.ApiResponse;
import com.codeboy.mvc.model.dto.response.DuplicateCheckResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.codeboy.mvc.model.service.MemberService;


import java.net.URI;
import java.util.HashMap;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/member")
@Tag(name="Member RESTful API", description = "Member CRUD를 할 수 있는 REST API")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //회원 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Member>> getMemberInfo( @AuthenticationPrincipal CustomUserDetails loginUser) {
        Long memberId = loginUser.getMemberId();
        try {
            Member member = memberService.getMemberById(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "회원정보가 조회되었습니다", member));

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(HttpStatus.NOT_FOUND, e.getMessage()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }
//특정 회원 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Member>> getOneMemberInfo( @PathVariable Long memberId) {
        try {
            Member member = memberService.getMemberById(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "회원정보가 조회되었습니다", member));

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(HttpStatus.NOT_FOUND, e.getMessage()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    //회원 탈퇴
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteMember( @AuthenticationPrincipal CustomUserDetails loginUser) {
        Long memberId = loginUser.getMemberId();
        try {
            memberService.deactivateMember(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "회원이 성공적으로 삭제되었습니다."));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(HttpStatus.NOT_FOUND, e.getMessage()));
        }
    }

    //회원 정보 업데이트
    @PatchMapping
    public ResponseEntity<ApiResponse<String>> updateMember(@RequestBody MemberUpdateRequest request,  @AuthenticationPrincipal CustomUserDetails loginUser) {
        Long memberId = loginUser.getMemberId();
        try {
            memberService.updateMember(memberId, request);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "회원정보 업데이트에 성공했습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(HttpStatus.NOT_FOUND, e.getMessage()));
        }
    }

    //ID, 닉네임, 이메일 중복체크
    @PostMapping("/check-id")
    public ResponseEntity<ApiResponse<DuplicateCheckResponse>> checkId(@RequestBody DuplicateCheckRequest request) {
        try {
            boolean duplicated = memberService.checkIdDuplicate(request.getValue());
            DuplicateCheckResponse response = new DuplicateCheckResponse(duplicated);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "아이디 중복 확인 완료", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/check-email")
    public ResponseEntity<ApiResponse<DuplicateCheckResponse>> checkEmail(@RequestBody DuplicateCheckRequest request) {
        try {
            boolean duplicated = memberService.checkEmailDuplicate(request.getValue());
            DuplicateCheckResponse response = new DuplicateCheckResponse(duplicated);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "이메일 중복 확인 완료", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/check-nickname")
    public ResponseEntity<ApiResponse<DuplicateCheckResponse>> checkNickname(@RequestBody DuplicateCheckRequest request) {
        try {
            boolean duplicated = memberService.checkNicknameDuplicate(request.getValue());
            DuplicateCheckResponse response = new DuplicateCheckResponse(duplicated);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "닉네임 중복 확인 완료", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }
}
