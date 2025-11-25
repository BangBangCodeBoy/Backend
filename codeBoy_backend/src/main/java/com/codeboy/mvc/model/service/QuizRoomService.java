package com.codeboy.mvc.model.service;

import java.util.List;

import com.codeboy.mvc.model.dto.QuizRoom;
import com.codeboy.mvc.model.dto.QuizRoomMember;

public interface QuizRoomService {

	//퀴즈방 전체 조회
	public List<QuizRoom> getQuizRoomList();

	//퀴즈 방 만들기 -> 생성된 퀴즈방 id를 return
	//퀴즈방 생성하고 생성된 퀴즈방에 방장 id 넣기
	public int createQuizRoom(long memberId);

	//참가자 채팅방 입장
	public void joinQuizRoom(QuizRoomMember quizRoomMember);

	//퀴즈룸 삭제
	public boolean deleteQuizRoom(long roomId);
}
