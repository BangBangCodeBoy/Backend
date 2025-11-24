package com.codeboy.mvc.model.dao;

import java.util.List;

import com.codeboy.mvc.model.dto.QuizRoom;
import com.codeboy.mvc.model.dto.QuizRoomMember;

public interface QuizRoomDao {
	
	//quiz_room 테이블. 퀴즈룸 생성. 생성된 퀴즈룸 id 반환
	public int insertQuizRoom();
	
	//quiz_room_member 테이블에 값 넣기 (퀴즈방 입장) - 참가자/호스트
	public void insertMemberToQuizRoom(QuizRoomMember quizRoomMember);

	
	//모든 퀴즈룸 조회하기
	public List<QuizRoom> selectAllQuizRoom();
	

	
	//하나의 퀴즈룸 조회(참가 멤버확인)
	public List<QuizRoomMember> selectOneQuizRoom(int roomId);
	
	//퀴즈룸 수정
	
	
	//퀴즈룸 삭제
	public void deleteQuizRoom(long roomId);

}
