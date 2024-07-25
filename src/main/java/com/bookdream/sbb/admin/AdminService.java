package com.bookdream.sbb.admin;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookdream.sbb.DataNotFoundException;
import com.bookdream.sbb.user.SiteUser;
import com.bookdream.sbb.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final UserRepository userRepository;

	public SiteUser getAdmin(String email) {
		Optional<SiteUser> siteUser = this.userRepository.findByEmail(email);
		if (siteUser.isPresent()) {
			SiteUser user = siteUser.get();
			if ("order".equals(user.getRole())) {
				throw new DataNotFoundException("Admin users cannot log in here.");
			}
			return user;
		} else {
			throw new DataNotFoundException("Site user not found!!");
		}
	}
}
