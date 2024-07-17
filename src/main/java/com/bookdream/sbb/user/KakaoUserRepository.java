package com.bookdream.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, Long> {

	KakaoUser findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
