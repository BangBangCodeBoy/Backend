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

    public Long joinProcess(JoinRequest req) {

        // 1) 중복 아이디 체크
        Boolean exists = memberDao.existByUserId(req.getId());

        if (Boolean.TRUE.equals(exists)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 2) Member 매핑
        Member member = new Member();
        member.setId(req.getId());
        member.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
        member.setNickname(req.getNickname());
        member.setEmail(req.getEmail());
        member.setIsActive(true);
        member.setRole("USER");

        // 3) DB Insert
        int rows = memberDao.insertMember(member);
        if (rows != 1) {
            throw new IllegalStateException("회원 가입에 실패했습니다. (insert rows = " + rows + ")");
        }

        return member.getMemberId();
    }
}
