package com.codeboy.mvc.security;

import com.codeboy.mvc.model.dao.MemberDao;
import com.codeboy.mvc.model.dto.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

//데이터베이스에서 사용자 정보를 조회하여 Spring Security에 사용
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberDao memberDao;

    public CustomUserDetailsService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public UserDetails loadUserByUsername(String ID) throws UsernameNotFoundException {
        Member member = memberDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.:" + ID));

        if (!member.getIsActive()) {
            throw new UsernameNotFoundException("비활성화된 계정입니다." +  ID);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getID())
                .password(member.getPassword())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!member.getIsActive())
                .build();
    }
}
