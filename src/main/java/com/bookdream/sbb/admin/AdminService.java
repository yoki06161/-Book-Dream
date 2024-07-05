package com.bookdream.sbb.admin;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookdream.sbb.DataNotFoundException;
import com.bookdream.sbb.user.SiteUser;
import com.bookdream.sbb.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   
   public SiteUser getAdmin(String email) {
      Optional<SiteUser> siteUser = this.userRepository.findByEmail(email);
      if (siteUser.isPresent()) {
         SiteUser user = siteUser.get();
         if ("admin".equals(user.getRole())) {
            throw new DataNotFoundException("Admin users cannot log in here.");
         }
         return user;
      } else {
         throw new DataNotFoundException("siteuser not found!!");
      }
   }
}