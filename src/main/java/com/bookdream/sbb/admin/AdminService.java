package com.bookdream.sbb.admin;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookdream.sbb.DataNotFoundException;
import com.bookdream.sbb.user.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   
   
   public SiteUser getAdmin(String username) {
      Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
      if(siteUser.isPresent()) {
         return siteUser.get();
      }else {
         throw new DataNotFoundException("siteuser not found!!");
      }
   }


}