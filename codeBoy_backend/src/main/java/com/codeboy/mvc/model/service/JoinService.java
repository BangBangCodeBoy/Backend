package com.codeboy.mvc.model.service;

import com.codeboy.mvc.model.dao.MemberDao;
import com.codeboy.mvc.model.dto.Member;
import com.codeboy.mvc.model.dto.request.JoinRequest;
import com.codeboy.mvc.model.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberDao memberDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinRequest request) {

        // 이미 같은 ID가 존재하면 가입 막기
        Member existing = memberDao.findByUserId(request.getId());
        if (existing != null) {
            return; // 또는 예외 던지기
        }

        Member member = new Member();
        member.setId(request.getId());
        member.setPassword(
                bCryptPasswordEncoder.encode(request.getPassword())
        );

        memberDao.insertMember(member);
    }
}
