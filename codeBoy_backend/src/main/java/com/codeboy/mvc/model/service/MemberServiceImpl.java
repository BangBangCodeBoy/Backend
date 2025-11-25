package com.codeboy.mvc.model.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeboy.mvc.model.dao.MemberDao;
import com.codeboy.mvc.model.dto.Member;
import com.codeboy.common.Status;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public int signUp(Member member) {
        // 기본값 세팅
        member.setStatus(Status.ACTIVE);
        member.setDeletedDate(null);
        member.setSignupDate(Timestamp.valueOf(LocalDateTime.now()));
        return memberDao.insertMember(member);
    }

    @Override
    public Member getMemberByMemberId(long memberId) {
        return memberDao.selectMemberByMemberId(memberId);
    }

  
    @Override
    public int updateMember(long memberId, Member member) {
        
        return memberDao.updateMember(memberId, member);
    }

    @Override
    public int deactivateMember(long memberId) {
        return memberDao.deleteMember(memberId);
    }
}
