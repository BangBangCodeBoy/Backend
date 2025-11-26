package com.codeboy.mvc.model.service;

import java.util.List;

import com.codeboy.mvc.model.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;

import com.codeboy.mvc.model.dao.QuizRoomDao;
import com.codeboy.mvc.model.dto.QuizRoom;
import com.codeboy.mvc.model.dto.QuizRoomMember;
import org.springframework.stereotype.Service;

@Service
public class QuizRoomServiceImpl implements QuizRoomService{
	@Autowired
	private QuizRoomDao quizRoomDao;


	@Override
	public List<QuizRoom> getQuizRoomList() {
		return quizRoomDao.selectAllQuizRoom();
	}

    @Override
    public List<QuizRoomMember> getOneQuizRoomMember(long roomId) {
        return quizRoomDao.selectOneQuizRoom( roomId);

    }

	@Override
	public long createQuizRoom() {
        QuizRoom room  = new QuizRoom();
        quizRoomDao.insertQuizRoom(room);
        //생성된 채팅방 id 리턴
		return room.getRoomId();

    }

	@Override
	public boolean joinQuizRoom(QuizRoomMember quizRoomMember) {
		 int rowsAffected = quizRoomDao.insertMemberToQuizRoom(quizRoomMember);
		 if (rowsAffected > 0) {
			 return true;
		 } else {
			 return false;
		 }

	}

	@Override
	public boolean deleteQuizRoom(long roomId) {
		boolean ok = quizRoomDao.deleteQuizRoom(roomId);
		return ok;
	}
}
