package com.codeboy.mvc.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.codeboy.mvc.model.dao.QuizRoomDao;
import com.codeboy.mvc.model.dto.QuizRoom;
import com.codeboy.mvc.model.dto.QuizRoomMember;
import org.springframework.stereotype.Service;

@Service
public class QuizRoomService{
	@Autowired
	private QuizRoomDao quizRoomDao;

	public List<QuizRoom> getQuizRoomList() {
		List<QuizRoom> quizRooms = quizRoomDao.selectAllQuizRoom();
        if (quizRooms == null) {
            throw  new IllegalStateException("퀴즈방 정보를 가져오지 못했습니다");
        }
        return quizRooms;
	}

    public List<QuizRoomMember> getOneQuizRoomMember(long roomId) {
        validateRoomId(roomId);

        List<QuizRoomMember> memberList = quizRoomDao.selectOneQuizRoom( roomId);

        if (!memberList.isEmpty()) {
            throw new IllegalStateException("퀴즈방에 참가자가 없습니다.");
        }
        return memberList;
    }

	public long createQuizRoom() {
        QuizRoom room  = new QuizRoom();
        quizRoomDao.insertQuizRoom(room);

        Long roomId = room.getRoomId();

        validateRoomId(roomId);
		return roomId;
    }

	public void joinQuizRoom(QuizRoomMember quizRoomMember) {
        Long memberId  = quizRoomMember.getMemberId();
        Long roomId = quizRoomMember.getRoomId();
        validateRoomId(roomId);

        //멤버 id가 실제로 존재하는지 확인
        if (memberId == null) {
            throw new IllegalArgumentException("멤버 ID가 유효하지 않습니다. :" );
        }
        //TODO : 채팅방 참여 인원이 초과되었는지 검증하는 로직 추가
        int rowAffected = quizRoomDao.insertMemberToQuizRoom(quizRoomMember);
        if (rowAffected <= 0) {
            throw new IllegalStateException("퀴즈방 입장에 실패하였습니다.");
        }
	}

	public void deleteQuizRoom(long roomId) {
        validateRoomId(roomId);
		boolean deleted = quizRoomDao.deleteQuizRoom(roomId);
        if (!deleted) {
            throw new IllegalStateException("퀴즈방 삭제에 실패했습니다.");
        }
	}

    private void validateRoomId(Long roomId) {
        if (roomId == null || roomId <= 0 || !quizRoomDao.existsQuizRoom(roomId)) {
            throw new IllegalArgumentException("퀴즈방 ID가 유효하지 않습니다. : " + roomId);
        }
    }
}
