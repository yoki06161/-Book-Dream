package com.bookdream.sbb.user;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoUserService {

    private final KakaoUserRepository kakaoUserRepository;

    public KakaoUser getUserByEmail(String email) {
        Optional<KakaoUser> kakaoUser = this.kakaoUserRepository.findByEmail(email);
        return kakaoUser.orElse(null);
    }

    @Transactional
    public KakaoUser createKakaoUser(String email, String nickname) {
        KakaoUser kakaoUser = new KakaoUser();
        kakaoUser.setEmail(email);
        kakaoUser.setUsername(nickname);
        return kakaoUserRepository.save(kakaoUser);
    }

    @Transactional
    public void deleteUser(KakaoUser user) {
        this.kakaoUserRepository.delete(user);
    }
}
