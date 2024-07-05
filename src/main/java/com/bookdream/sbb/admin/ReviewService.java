package com.bookdream.sbb.admin;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bookdream.sbb.DataNotFoundException;
import com.bookdream.sbb.user.SiteUser;
import com.bookdream.sbb.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public SiteUser getReview(String email) {
        Optional<SiteUser> siteUser = this.userRepository.findByEmail(email);
        if (siteUser.isPresent()) {
            SiteUser user = siteUser.get();
            if ("review".equals(user.getRole())) { 
                throw new DataNotFoundException("Review users cannot log in here.");
            }
            return user;
        } else {
            throw new DataNotFoundException("siteuser not found!!");
        }
    }
}
