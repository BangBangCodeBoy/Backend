package com.codeboy.mvc.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.codeboy.mvc.model.dao.QuizRoomDao;
import com.codeboy.mvc.model.dto.QuizRoom;
import com.codeboy.mvc.model.dto.QuizRoomMember;

public class QuizRoomServiceImpl implements QuizRoomService{
	@Autowired
	private QuizRoomDao quizRoomDao;


	@Override
	public List<QuizRoom> getQuizRoomList() {
		return quizRoomDao.selectAllQuizRoom();
	}

	@Override
	public int createQuizRoom(long memberId) {
		return quizRoomDao.insertQuizRoom();
	}

	@Override
	public void joinQuizRoom(QuizRoomMember quizRoomMember) {
		 quizRoomDao.insertMemberToQuizRoom(quizRoomMember);
		 return;

	}

	@Override
	public boolean deleteQuizRoom(long roomId) {
		boolean ok = quizRoomDao.deleteQuizRoom(roomId);
		return ok;
	}

}
