package com.codeboy.mvc.model.dao;

import com.codeboy.mvc.model.dto.Member;
import com.codeboy.mvc.model.dto.request.MemberUpdateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface MemberDao {
    public void insertMember(Member member);

    public Member selectMemberById(Long memberId);

    //멤버 삭제 -> db에서는 status 변경
    public int deactivateMemberById(Long memberId);

    //멤버 업데이트 -> db에서는 patch(nickname, email, id)
    public int updateMemberById(@Param("memberId") Long memberId, @Param("update") MemberUpdateRequest memberUpdateRequest);

    //아이디 중복 체크
    boolean existsId(String id);

    //닉네임 중복 체크
    boolean existsNickname(String nickname);

    //이메일 중복 체크
    boolean existsEmail(String email);

    //멤버가 활성화 상태인지 확인
    boolean isMemberActive(Long memberId);

}
