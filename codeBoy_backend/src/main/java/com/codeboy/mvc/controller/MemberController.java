package com.codeboy.mvc.controller;


import com.codeboy.mvc.model.requestDto.MemberUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import com.codeboy.mvc.model.dto.request.DuplicateCheckRequest;
import com.codeboy.mvc.model.dto.request.MemberUpdateRequest;
import com.codeboy.mvc.model.dto.response.ApiResponse;
import com.codeboy.mvc.model.dto.response.DuplicateCheckResponse;
import com.codeboy.mvc.model.service.IncorrectNoteService;
import com.codeboy.mvc.model.service.MemberService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codeboy.mvc.model.dto.Member;
import com.codeboy.mvc.model.requestDto.LoginRequest;
import com.codeboy.mvc.model.service.MemberService;

import java.net.URI;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@Tag(name="Member RESTful API", description = "Member CRUD를 할 수 있는 REST API")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 회원가입
     * POST /api/members
     * RequestBody: { id, password, nickname, email }
     * Response:
     *   201 생성 성공
     *   400 잘못된 요청
     *   409 아이디/이메일 중복
     */
    @PostMapping("/member")
    public ResponseEntity<?> signUp(@RequestBody Member member) {
        // TODO: 아이디/이메일 중복 체크 로직은 나중에 추가
        int result = memberService.signUp(member);

        if (result == 1) {
            // Location 헤더에 새로 생성된 리소스 URI 넣어줄 수도 있음
            URI location = URI.create("/api/members/" + member.getMemberId());
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(201))
                    .location(location)
                    .body("회원가입 성공");
        } else {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(400))
                    .body("회원가입 실패");
        }
        //아이디/이메일 중복로직은 나중에 구
    }

    /**
     * 로그인
     * POST /api/auth/login
     * RequestBody: { id, password }
     * Response:
     *   200 성공 시 { token, memberId, nickname }
     *   401 실패 시 { error: "로그인 실패" }
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		return null;
    }

    /**
     * 로그아웃
     * POST /api/auth/logout
     * RequestBody: 없음
     * Response:
     *   204 성공 시 본문 없음
     */
    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout() {
       return null;
    }

    /**
     * 내 정보 수정
     * PUT /api/members/me
     * RequestBody: { nickname?, email?, password? }
     * Response:
     *   200 성공 시 수정된 정보 or 메시지
     *   400 잘못된 요청
     *   401 인증 실패
     */
    //회원정보 전체를 업데이트하지않고 일부만 수정하는 거라서 Put에서 Patch매핑으로 변경
    @PatchMapping("/members/{memberId}")
    public ResponseEntity<?> updateMe(@RequestBody MemberUpdateRequest request, @PathVariable long memberId ) {
        //파라미터로 memberId받아서 해당회원의 정보를 수정

        Member member = memberService.getMemberByMemberId(memberId);
        //회원을 못찾는 경우 ->존재하지 않는 memberId인경우
        if (member == null) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(401))
                    .body("인증된 회원을 찾을 수 없습니다.");
        }

        //닉네임과 이메일은 바꿀 수 있다고 가정. 필요하면 ID도..?
        if (request.getNickName() != null) {
            member.setNickname(request.getNickName());
        }
        if (request.getEmail() != null) {
            member.setEmail(request.getEmail());
        }


    //회원 조회
    @GetMapping
    //TODO: memberID 넣기
    public ResponseEntity<ApiResponse<Member>> getMemberInfo() {
        Long memberId = 1L;
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
    public ResponseEntity<ApiResponse<String>> deleteMember() {
        //TODO : memberId 받아오기
        Long memberId = 1L;
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
    public ResponseEntity<ApiResponse<String>> updateMember(@RequestBody MemberUpdateRequest request) {
        //TODO : memberId 받아오기
        Long memberId = 1L;
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
