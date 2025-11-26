package com.codeboy.mvc.controller;
import com.codeboy.mvc.model.dto.*;
import com.codeboy.mvc.model.requestDto.CreateQuizRoomRequest;
import com.codeboy.mvc.model.requestDto.JoinQuizRoomRequest;
import com.codeboy.mvc.model.responseDto.ApiResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codeboy.mvc.model.service.QuizRoomServiceImpl;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/quiz-room")

public class QuizRoomController {
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private QuizRoomServiceImpl quizRoomService;

    @PostConstruct
    public void testConnection() throws SQLException {
        System.out.println("연결확인" + dataSource.getConnection());
    }

	//호스트 - 퀴즈방 만들기
	@PostMapping("/create")
    //TODO : 로그인 구현되면 memberId를 requestBody로 넘기지 말고 세션에서 가져오도록 하기
	public ResponseEntity<ApiResponse<Long>> createQuizRoom(@RequestBody CreateQuizRoomRequest request) {
        long memberId =  request.getMemberId();

		//1. memberId가 유효한지 체크
		//TODO : membertable에서 해당 멤버가 있는지 확인하는 조건 추가
		if (memberId <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "멤버ID가 유효하지 않습니다.", null
            ));
		}

		//2. 새로운 채팅방 생성하기
		long quizRoomId = quizRoomService.createQuizRoom();
        if (quizRoomId <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "새로운 퀴즈방 생성에 실패했습니다. ", null));
        }
		//3. QuizRoomMember 객체 생성
		QuizRoomMember quizRoomMember = new QuizRoomMember();
		//4. setter로 객체 만들기
		quizRoomMember.setMemberId(memberId);
		quizRoomMember.setRoomId(quizRoomId);
		quizRoomMember.setIsHost(true);

		//5. 채팅방에 넣기
		boolean isOk = quizRoomService.joinQuizRoom(quizRoomMember);

		if (!isOk) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "호스트를 퀴즈방에 넣는 과정이 실패했습니다. ", null));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "퀴즈방이 성공적으로 생성되었습니다.", quizRoomId));
	}

	//채팅방 참여하기
	@PostMapping("/join")
    //TODO : 로그인 구현되면 memberId를 requestBody로 넘기지 말고 세션에서 가져오도록 하기
	public ResponseEntity<ApiResponse<String>> joinQuizRoom(@RequestBody JoinQuizRoomRequest request) {
        long memberId =  request.getMemberId();
        long roomId = request.getRoomId();
        //TODO : 멤버 ID가 DB에 있는지 확인하는 과정
        if (memberId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "멤버ID가 유효하지 않습니다.", null));
        }
        //TODO : 채팅방 인원이 초과되었는지 확인하는 메서드 추가
        //퀴즈방 기본 검증
        if (roomId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "퀴즈방ID가 유효하지 않습니다.", null));
        }
        //퀴즈방이 존재하는지 확인
        boolean roomExists = quizRoomService.existsQuizRoom(roomId);
        if (!roomExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "존재하지 않는 퀴즈방입니다.", null)
            );
        }
		//QuizRoomMember 생성
		QuizRoomMember quizRoomMember = new QuizRoomMember();
		quizRoomMember.setIsHost(false);
		quizRoomMember.setMemberId(memberId);
		quizRoomMember.setRoomId(roomId);

		//채팅방에 넣기
		boolean isOk = quizRoomService.joinQuizRoom(quizRoomMember);

		if (!isOk) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "퀴즈방 입장에 실패하였습니다. ", null));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "퀴즈방 입장에 성공하였습니다.", null));
    }

    //채팅방 목록 보여주기
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuizRoom>>> getQuizRoomList() {
        List<QuizRoom> quizRooms = quizRoomService.getQuizRoomList();
        if (quizRooms == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "퀴즈방 정보를 가져오지 못했습니다", null));
        }
        else if (quizRooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false,"생성된 퀴즈방이 없습니다.",  null));
        }
        return ResponseEntity.status(HttpStatus.OK) .body(new ApiResponse<>(true, "퀴즈방 목록 조회 성공", quizRooms));
    }



	//현재 참가자 목록 보여주기
    @GetMapping("/{roomId}/member")
    public ResponseEntity<ApiResponse<List<QuizRoomMember>>> getQuizRoomMembers(@PathVariable long roomId) {
        if (roomId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "퀴즈방 ID가 유효하지 않습니다.", null));
        }

        List<QuizRoomMember> memberList = quizRoomService.getOneQuizRoomMember(roomId);

        //TODO : member API 와 연결해서 참가 중인 멤버 닉네임을 보여주도록?
        if (!memberList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "멤버 리스트를 반환합니다.", memberList ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "참가자가 없습니다.", null));

    }

	//채팅방 삭제하기
    @DeleteMapping("/{roomId}")
    public ResponseEntity<ApiResponse<String>> deleteQuizRoom(@PathVariable long roomId) {
        if (roomId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "퀴즈방 ID가 유효하지 않습니다.", null));
        }
        boolean isOk = quizRoomService.deleteQuizRoom(roomId);
        if (!isOk) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "퀴즈방 삭제에 실패했습니다.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "성공적으로 퀴즈방이 삭제되었습니다. ", null
        ));
    }

}
