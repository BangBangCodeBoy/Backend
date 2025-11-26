package com.codeboy.mvc.model.service;

import java.util.List;

import com.codeboy.mvc.model.dto.Member;
import org.springframework.stereotype.Service;

import com.codeboy.mvc.model.dto.QuizRoom;
import com.codeboy.mvc.model.dto.QuizRoomMember;
@Service
public interface QuizRoomService {

	//퀴즈방 전체 조회
	public List<QuizRoom> getQuizRoomList();

	//퀴즈 방 만들기 -> 생성된 퀴즈방 id를 return
	public int createQuizRoom();

    public  List<QuizRoomMember> getOneQuizRoomMember(long roomId);

	//참가자 채팅방 입장
	public boolean joinQuizRoom(QuizRoomMember quizRoomMember);

	//퀴즈룸 삭제
	public boolean deleteQuizRoom(long roomId);
}
