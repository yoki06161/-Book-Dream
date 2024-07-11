package com.bookdream.sbb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoUserService {

    private final KakaoUserRepository kakaoUserRepository;
    
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly=true)
    public KakaoUser findKakaoUser(String email) {
    	
    	KakaoUser user = kakaoUserRepository.findByEmail(email).get();
    	return user;
    }
    

    @Transactional
    public void createKakaoUser(KakaoUser kakaoUser) {
    	String rawPassword = kakaoUser.getPassword();
    	String encPassword = passwordEncoder.encode(rawPassword);
    	kakaoUser.setPassword(encPassword);
        kakaoUserRepository.save(kakaoUser);
    }

}
