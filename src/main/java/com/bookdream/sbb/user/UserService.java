package com.bookdream.sbb.user;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookdream.sbb.DataNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(Map<String, String> map) {
        // 중복 이메일 검사
        if (userRepository.existsByEmail(map.get("email"))) {
            throw new DataIntegrityViolationException("이미 등록된 이메일입니다.");
        }

        // 중복 사용자 이름 검사
        if (userRepository.existsByUsername(map.get("username"))) {
            throw new DataIntegrityViolationException("이미 등록된 사용자 이름입니다.");
        }

        SiteUser user = new SiteUser();
        user.setUsername(map.get("username"));
        user.setEmail(map.get("email"));
        user.setPassword(passwordEncoder.encode(map.get("password")));
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String email) {
        Optional<SiteUser> siteUser = this.userRepository.findByEmail(email);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found!!");
        }
    }

    @Transactional
    public SiteUser createKakaoUser(String email, String nickname) {
        SiteUser user = new SiteUser();
        user.setEmail(email);
        user.setUsername(nickname);
        user.setPassword(""); // 카카오 로그인 사용자는 비밀번호가 없을 수 있음
        System.out.println("Saving new user: " + user);
        return userRepository.save(user);
    }

    public SiteUser getUserByEmail(String email) {
    	Optional<SiteUser> siteUser = this.userRepository.findByEmail(email);
    	if (siteUser.isPresent()) {
            return siteUser.get();
        }else {
            throw new DataNotFoundException("siteuser not found!!");
        }
    }
}