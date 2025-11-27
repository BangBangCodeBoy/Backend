package com.codeboy.mvc.model.dao;

import com.codeboy.mvc.model.dto.Member;
import com.codeboy.mvc.model.requestDto.MemberUpdateRequest;

public interface MemberDao {
	 // 회원 가입
    int insertMember(Member member);

    // PK로 조회
    Member selectMemberByMemberId(long memberId);

    // 닉네임/이메일/비밀번호 수정
    int updateMember(long memberId, Member member);

    // 회원 비활성화(탈퇴) - status, isDeleted 업데이트
    int deleteMember(long memberId);
    
}
