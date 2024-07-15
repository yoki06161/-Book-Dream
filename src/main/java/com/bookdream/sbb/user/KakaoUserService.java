package com.bookdream.sbb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KakaoUserService {

    private final KakaoUserRepository kakaoUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public KakaoUserService(KakaoUserRepository kakaoUserRepository) {
        this.kakaoUserRepository = kakaoUserRepository;
    }

    @Transactional(readOnly = true)
    public KakaoUser findKakaoUser(String email) {
        return kakaoUserRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public void createKakaoUser(KakaoUser kakaoUser) {
        // 패스워드 암호화
        String encodedPassword = passwordEncoder.encode(kakaoUser.getPassword());
        kakaoUser.setPassword(encodedPassword);
        kakaoUserRepository.save(kakaoUser);
    }
}

