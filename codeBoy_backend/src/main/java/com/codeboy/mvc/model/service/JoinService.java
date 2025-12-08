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

    public void joinProcess(JoinRequest req) {
        // 1) 요청 값 로그 찍기
        System.out.println("JOIN REQ = id=" + req.getId()
                + ", nickname=" + req.getNickname()
                + ", email=" + req.getEmail());

        // 2) 중복 아이디 체크
        Boolean exists = memberDao.existByUserId(req.getId());
        System.out.println("existByUserId = " + exists);

        if (Boolean.TRUE.equals(exists)) {
            System.out.println("이미 존재하는 아이디라서 insert 안 함");
            return;
        }

        // 3) Member 매핑
        Member member = new Member();
        member.setId(req.getId());
        member.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
        member.setNickname(req.getNickname());
        member.setEmail(req.getEmail());
        member.setIsActive(true);
        member.setRole("USER");

        // 4) insert 실행
        int rows = memberDao.insertMember(member);
        System.out.println("insertMember rows = " + rows);


        // 🔥 방금 넣은 회원 다시 조회
        Member saved = memberDao.findByUserId(member.getId());
        System.out.println("after insert findByUserId = " + saved);

        String dbName = memberDao.currentDatabase();
        System.out.println("==> APP is connected to DB: " + dbName);

    }
}
