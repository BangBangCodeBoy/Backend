package com.codeboy.mvc.controller;

import com.codeboy.mvc.model.dto.CustomUserDetails;
import com.codeboy.mvc.model.dto.UserProblem;
import com.codeboy.mvc.model.dto.response.ApiResponse; // 실제 패키지에 맞게 수정
import com.codeboy.mvc.model.service.UserProblemService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-problems")
@Tag(name="User-problems RESTful API", description = "User-problems CRUD를 할 수 있는 REST API")
public class UserProblemController {


    private final UserProblemService userProblemService;

    public UserProblemController(UserProblemService userProblemService) {
        this.userProblemService = userProblemService;
    }

    // 특정 문제세트 안의 모든 문제 조회
    @GetMapping("/sets/{userProblemSetId}")
    public ResponseEntity<ApiResponse<List<UserProblem>>> getProblemsByUserProblemSetId(
            @PathVariable Long userProblemSetId
    ) {
        try {
            List<UserProblem> problems = userProblemService.getProblemsByUserProblemSetId(userProblemSetId);

            if (problems == null || problems.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.failure(HttpStatus.NOT_FOUND, "해당 문제세트에 등록된 문제가 없습니다."));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success(HttpStatus.OK, "문제 목록 조회 성공", problems));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류로 문제 목록을 조회하지 못했습니다."));
        }
    }

    // user_problem테이블에 문제들 일괄 등록
    @PostMapping("/sets/{userProblemSetId}")
    public ResponseEntity<ApiResponse<Void>> createUserProblems(
            @PathVariable Long userProblemSetId,
            @RequestBody List<UserProblem> userProblems,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        System.out.println("문제세트 번호: "+ userProblemSetId);
        Long memberId= loginUser.getMemberId();
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."));
        }

        try {
            // 세트 ID 세팅
            for (UserProblem p : userProblems) {
                p.setUserProblemSetId(userProblemSetId);
            }
            System.out.println("디버깅1");

            int inserted = userProblemService.addUserProblems(userProblems);
            System.out.println("디버깅2");

            if (inserted == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "문제 등록에 실패했습니다."));
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(HttpStatus.CREATED, "문제 등록 성공", null));

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류로 문제를 등록하지 못했습니다."));
        }
    }

    // 문제 수정
    @PutMapping("/{userProblemId}")
    public ResponseEntity<ApiResponse<Void>> updateUserProblem(
            @PathVariable Long userProblemId,
            @RequestBody UserProblem userProblem,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        Long memberId=  loginUser.getMemberId();
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."));
        }

        try {
            userProblem.setUserProblemId(userProblemId);

            int result = userProblemService.updateUserProblem(userProblem);

            if (result == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.failure(HttpStatus.NOT_FOUND, "수정할 문제가 존재하지 않습니다."));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success(HttpStatus.OK, "문제 수정 성공", null));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류로 문제를 수정하지 못했습니다."));
        }
    }

    // 문제 삭제
    @DeleteMapping("/{userProblemId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserProblem(
            @PathVariable Long userProblemId,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        Long memberId=  loginUser.getMemberId();
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."));
        }

        try {
            int result = userProblemService.deleteUserProblem(userProblemId);

            if (result == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.failure(HttpStatus.NOT_FOUND, "삭제할 문제가 존재하지 않습니다."));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success(HttpStatus.OK, "문제 삭제 성공", null));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류로 문제를 삭제하지 못했습니다."));
        }
    }
}
