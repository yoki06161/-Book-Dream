//package com.bookdream.sbb.user;
//
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.bookdream.sbb.DataNotFoundException;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//	private final UserRepository userRepository;
//	private final PasswordEncoder passwordEncoder;
//	
//	
//	public SiteUser create(Map<String, String> map) {
//		SiteUser user = new SiteUser();
//		user.setUsername(map.get("username"));
//		user.setEmail(map.get("email"));		
//		user.setPassword(passwordEncoder.encode(map.get("password")));
//		this.userRepository.save(user);
//		return user;
//	}
//	
//	public SiteUser getUser(String username) {
//		Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
//		if(siteUser.isPresent()) {
//			return siteUser.get();
//		}else {
//			throw new DataNotFoundException("siteuser not found!!");
//		}
//	}
//	
//	
//}
//
//
//
//
//
//
//
//
//
