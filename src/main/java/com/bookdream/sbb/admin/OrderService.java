package com.bookdream.sbb.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final UserRepository userRepository;
}

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}