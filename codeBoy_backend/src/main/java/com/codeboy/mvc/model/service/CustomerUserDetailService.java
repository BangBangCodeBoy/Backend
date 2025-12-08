package com.codeboy.mvc.model.service;

import com.codeboy.mvc.model.dao.MemberDao;
import com.codeboy.mvc.model.dto.CustomUserDetails;
import com.codeboy.mvc.model.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {

    // MemberDao는 Spring이 주입해야 함 (NEW 하면 안됨)
    private final MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member userData = memberDao.findByUserId(username);

        if (userData == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new CustomUserDetails(userData);
    }
}
