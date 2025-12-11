package com.codeboy.mvc.model.service;

import java.util.List;

import com.codeboy.mvc.model.dto.response.GetQuizRoomMembersResponse;
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

    public List<GetQuizRoomMembersResponse> getOneQuizRoomMember(long roomId) {
        validateRoomId(roomId);

        List<GetQuizRoomMembersResponse> memberList = quizRoomDao.selectOneQuizRoom(roomId);
        if (memberList.isEmpty()) {
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
        memberDuplicateCheck(memberId);

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
        boolean existRoom = quizRoomDao.existsQuizRoom(roomId) > 0;
        if (roomId == null || roomId <= 0 || !existRoom) {
            throw new IllegalArgumentException("퀴즈방 ID가 유효하지 않습니다. : " + roomId);
        }
    }

    private void memberDuplicateCheck(Long memberId) {
        boolean duplicated = quizRoomDao.isDuplicatedMember(memberId) > 0;
        if (duplicated) {
            throw new IllegalArgumentException("한 멤버는 하나의 채팅방에만 들어갈 수 있습니다.");
        }
    }

    public void leaveQuizRoom(long roomId, long memberId) {

        // 1) 현재 방에 이 멤버가 실제로 있는지 확인
        QuizRoomMember quizRoomMember = quizRoomDao.selectQuizRoomMember(roomId, memberId);

        if (quizRoomMember == null) {
            throw new IllegalArgumentException("해당 멤버는 이 퀴즈방에 참가 중이 아닙니다.");
        }

        // TODO: 호스트가 나갈 때의 정책이 따로 있다면 여기서 처리
        // if (quizRoomMember.getIsHost()) { ... }

        // 2) 퀴즈방 멤버 목록에서 삭제
        int deleted = quizRoomDao.deleteQuizRoomMember(roomId, memberId);

        if (deleted != 1) {
            throw new IllegalStateException("퀴즈방 퇴장 처리에 실패했습니다.");
        }

        // TODO: 마지막 사람이 나가면 방 자체를 삭제할지 여부는 여기서 추가로 정책 결정 가능
        // int remains = quizRoomDao.countMembersInRoom(roomId);
        // if (remains == 0) { quizRoomDao.deleteQuizRoom(roomId); }
    }

}
