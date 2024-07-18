package com.bookdream.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    
    // 두목님 테스트. 로그인 이름 찾기
	Optional<SiteUser> findByusername(String user);
}
