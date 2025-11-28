package com.codeboy.mvc.model.service;

import com.codeboy.mvc.model.dto.Member;
import com.codeboy.mvc.model.dto.request.MemberUpdateRequest;

public interface MemberService {
    //회원가입
	public void signupMember();

	//회원 정보 가져오기기
    public Member readMember(String id, String password);

	//회원 탈퇴
    public void withdrawal(int memberId);

    //회원정보 수정-> db에서는 patch(nickname, email, id)
    public void updateMember(long memberId, MemberUpdateRequest memberUpdateRequest);
}
