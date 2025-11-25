package com.codeboy.mvc.model.dao;

import com.codeboy.mvc.model.dto.Member;
import com.codeboy.mvc.model.dto.MemberUpdateRequest;

public interface MemberDao {
    public void insertMember(Member member);

    public Member selectMember(String id, String password);

    //멤버 삭제 -> db에서는 status 변경
    public void delete(int memberId);

    //멤버 업데이트 -> db에서는 patch(nickname, email, id)
    public void updateMember(long memberId, MemberUpdateRequest memberUpdateRequest);


}
