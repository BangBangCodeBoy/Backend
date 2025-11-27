package com.codeboy.mvc.model.service;

import com.codeboy.mvc.model.dto.Member;
import com.codeboy.mvc.model.requestDto.MemberUpdateRequest;

public interface MemberService {

    // 회원 가입
    int signUp(Member member);

    // PK로 회원 조회 (마이페이지 같은 용도)
    Member getMemberByMemberId(long memberId);

    // 내 정보 수정
    int updateMember(long memberId, Member member);

    // 회원 비활성화(탈퇴)
    int deactivateMember(long memberId);
    //회원정보 수정-> db에서는 patch(nickname, email, id)
    public void updateMember(long memberId, MemberUpdateRequest memberUpdateRequest);
}
