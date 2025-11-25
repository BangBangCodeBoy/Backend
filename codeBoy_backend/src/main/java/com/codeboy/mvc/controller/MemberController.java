package com.codeboy.mvc.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codeboy.mvc.model.dto.Member;
import com.codeboy.mvc.model.dto.LoginRequest;
import com.codeboy.mvc.model.dto.MemberUpdateRequest;
import com.codeboy.mvc.model.service.MemberService;

@RestController
@RequestMapping("/api")
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
    @PostMapping("/members")
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
    @PutMapping("/members/{memberId}")
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

        
        int result = memberService.updateMember(memberId, member);

        if (result == 1) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .body(member); // 또는 "수정 성공" 메시지
        } else {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(400))
                    .body("회원정보 수정 실패");
        }
    }

    /**
     * 회원 탈퇴 (비활성화)
     * DELETE /api/members/me
     * Response:
     *   204 성공 시 본문 없음
     *   401 인증 실패
     */
    @DeleteMapping("/members/me")
    public ResponseEntity<?> deleteMe() {
        // TODO: 실제 구현에서는 JWT/세션에서 memberId를 가져와야 함.
        long currentMemberId = 1L;

        Member member = memberService.getMemberByMemberId(currentMemberId);
        if (member == null) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(401))
                    .body("인증된 회원을 찾을 수 없습니다.");
        }

        int result = memberService.deactivateMember(currentMemberId);

        if (result == 1) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(204))
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(400))
                    .body("회원 탈퇴 처리 실패");
        }
    }
}
