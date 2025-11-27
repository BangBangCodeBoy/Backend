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
		return quizRoomDao.selectAllQuizRoom();
	}

    public List<QuizRoomMember> getOneQuizRoomMember(long roomId) {
        return quizRoomDao.selectOneQuizRoom( roomId);

    }

	public long createQuizRoom() {
        QuizRoom room  = new QuizRoom();
        quizRoomDao.insertQuizRoom(room);
        //생성된 채팅방 id 리턴
		return room.getRoomId();

    }

	public boolean joinQuizRoom(QuizRoomMember quizRoomMember) {
		 int rowsAffected = quizRoomDao.insertMemberToQuizRoom(quizRoomMember);
		 if (rowsAffected > 0) {
			 return true;
		 } else {
			 return false;
		 }

	}

	public boolean deleteQuizRoom(long roomId) {
		boolean ok = quizRoomDao.deleteQuizRoom(roomId);
		return ok;
	}
    public boolean existsQuizRoom(long roomId){
        return quizRoomDao.existsQuizRoom(roomId) > 0;
    }
}
